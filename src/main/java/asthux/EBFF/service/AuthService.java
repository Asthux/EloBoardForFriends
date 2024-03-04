package asthux.EBFF.service;

import asthux.EBFF.domain.member.Member;
import asthux.EBFF.domain.token.Token;
import asthux.EBFF.enums.ReturnCode;
import asthux.EBFF.exception.EbffLogicException;
import asthux.EBFF.exception.EbffRequestException;
import asthux.EBFF.param.MemberLoginParam;
import asthux.EBFF.provider.JwtProvider;
import asthux.EBFF.repository.MemberRepository;
import asthux.EBFF.repository.RedisTokenRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final MemberRepository memberRepository;

  private final JwtProvider jwtProvider;

  private final RedisTokenRepository redisTokenRepository;

  public Token verifyLogin(MemberLoginParam param) {
    Member member = memberRepository.findByMemberName(param.getMemberName())
                                    .orElseThrow(() -> new EbffRequestException(ReturnCode.NOT_FOUND_ENTITY));

    boolean isMatched = BCrypt.checkpw(param.getPassword(), member.getPassword());

    if (isMatched) {
      Token token = jwtProvider.createToken(member);
      redisTokenRepository.save(token);
      return token;
    } else {
      throw new EbffRequestException(ReturnCode.WRONG_PASSWORD);
    }
  }

  public void verifyLogout() {
    String stringToken = jwtProvider.getToken();
    jwtProvider.verifyToken(stringToken);
    Token token = Token.builder()
                       .jwtToken(stringToken)
                       .build();
    redisTokenRepository.delete(token);
  }

  public void isAuthorityMatched(Long objectId, Long loginMemberId) {
    if (!objectId.equals(loginMemberId)) {
      throw new EbffLogicException(ReturnCode.NOT_AUTHORIZED);
    }
  }
}

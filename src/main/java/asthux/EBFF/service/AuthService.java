package asthux.EBFF.service;

import asthux.EBFF.domain.member.Member;
import asthux.EBFF.enums.ReturnCode;
import asthux.EBFF.exception.EbffRequestException;
import asthux.EBFF.param.MemberLoginParam;
import asthux.EBFF.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final MemberRepository memberRepository;

  public Member verifyLogin(MemberLoginParam param) {
    Member member = memberRepository.findByMemberName(param.getMemberName())
                                    .orElseThrow(() -> new EbffRequestException(ReturnCode.NOT_FOUND_ENTITY));

    boolean isMatched = BCrypt.checkpw(param.getPassword(), member.getPassword());

    if (isMatched) {
      return member;
    } else {
      throw new EbffRequestException(ReturnCode.WRONG_PASSWORD);
    }
  }
}

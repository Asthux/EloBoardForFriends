package asthux.EBFF.provider;

import asthux.EBFF.domain.member.Member;
import asthux.EBFF.domain.token.Token;
import asthux.EBFF.enums.ReturnCode;
import asthux.EBFF.exception.EbffRequestException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@RequiredArgsConstructor
public class JwtProvider {

  @Value("${spring.jwt.secretKey}")
  private String secretKey;

  // 1. 토큰 생성
  public Token createToken(Member member) {
    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    String jwt = JWT.create()
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 1000000L))
                    .withClaim("memberId", member.getMemberId())
                    .withClaim("memberName", member.getMemberName())
                    .withClaim("nickName", member.getNickName())
                    .withClaim("role", member.getRole().name())
                    .sign(algorithm);

    Token token = Token.builder()
                       .jwtToken(jwt)
                       .memberId(member.getMemberId())
                       .build();
    return token;
  }

  public Long getMemberId(DecodedJWT decodedJWT) {
    Long memberId = decodedJWT.getClaim("memberId").asLong();
    return memberId;
  }

  public String getNickName(DecodedJWT decodedJWT) {
    String nickName = decodedJWT.getClaim("nickName").asString();
    return nickName;
  }

  public DecodedJWT verifyToken(String token) {
    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    try {
      DecodedJWT decodedJWT = JWT.require(algorithm)
                                 .build().verify(token);
      return decodedJWT;
    } catch (JWTVerificationException verificationEx) {
      throw new EbffRequestException(ReturnCode.NOT_AUTHORIZED);
    }
  }

  public String getToken() {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    if (request.getHeader("X-ACCESS-TOKEN") == null) {
      throw new EbffRequestException(ReturnCode.NOT_AUTHORIZED);
    } else {
      return request.getHeader("X-ACCESS-TOKEN");
    }
  }
}

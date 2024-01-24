package asthux.EBFF.provider;

import asthux.EBFF.domain.member.Member;
import asthux.EBFF.domain.token.Token;
import asthux.EBFF.enums.ReturnCode;
import asthux.EBFF.exception.EbffLogicException;
import asthux.EBFF.exception.EbffRequestException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

  public Token createToken(Member member) {
    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    String memberJson = generateMemberClaim(member);

    String jwt = JWT.create()
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 60 * 60 * 1000L))
                    .withClaim("member", memberJson)
                    .sign(algorithm);

    Token token = Token.builder()
                       .jwtToken(jwt)
                       .build();
    return token;
  }

  public String generateMemberClaim(Member member) {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());

    try {
      return objectMapper.writeValueAsString(member);
    } catch (JsonProcessingException e) {
      throw new EbffLogicException(ReturnCode.INTERNAL_ERROR);
    }
  }
  public Member extractMemberClaim(DecodedJWT decodedJWT) {
    String memberJson = decodedJWT.getClaim("member").asString();

    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.readValue(memberJson, Member.class);
    } catch (JsonProcessingException e) {
      throw new EbffLogicException(ReturnCode.INTERNAL_ERROR);
    }
  }

  public DecodedJWT verifyToken(String token) {
    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    try {
      return JWT.require(algorithm)
                .build().verify(token);
    } catch (TokenExpiredException tokenExpiredException) {
      throw new EbffRequestException(ReturnCode.EXPIRED_TOKEN);
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

package asthux.EBFF.controller;

import asthux.EBFF.domain.member.Member;
import asthux.EBFF.domain.token.Token;
import asthux.EBFF.enums.ReturnCode;
import asthux.EBFF.param.MemberLoginParam;
import asthux.EBFF.provider.JwtProvider;
import asthux.EBFF.repository.RedisTokenRepository;
import asthux.EBFF.response.ApiResponse;
import asthux.EBFF.service.AuthService;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
  /*
   * 1. AuthController.login 구현하기
   * 2. AuthController.logout 구현하기
   * 3. MemberController에 비밀번호 저장 시 암호화
   * 4. JwtProvider.createToken 구현하기
   * 5. redis 붙이기 (implementation 'org.springframework.boot:spring-boot-starter-data-redis')
   * */

  private final AuthService authService;

  @Autowired
  private final JwtProvider provider;

  private final RedisTokenRepository tokenRepository;

  @PostMapping("/login")
  public ApiResponse<?> login(@RequestBody MemberLoginRequest request) {
    MemberLoginParam param = request.convert();
    // id, password에 해당하는 유저가 있는지 확인
    Member member = authService.verifyLogin(param);
    // 있으면 JWT 토큰 생성
    Token token = provider.createToken(member);
    // redis에 토큰 저장 ( 만료시간 X )
    tokenRepository.save(token);
    // 토큰을 FE에 응답
    return ApiResponse.of(TokenItem.of(token));
  }

  @DeleteMapping("/logout")
  public ApiResponse<?> logout() {
    // redis에 저장된 토큰 삭제
    String stringToken = provider.getToken();
    DecodedJWT decodedJWT = provider.verifyToken(stringToken);
    Token token = Token.builder()
                       .memberId(provider.getMemberId(decodedJWT))
                       .jwtToken(stringToken)
                       .build();
    tokenRepository.delete(token);
    return ApiResponse.of(ReturnCode.SUCCESS);
  }

  @Data
  private static class TokenItem {

    private String jwtToken;

    private static TokenItem of(Token token) {
      TokenItem converted = new TokenItem();
      converted.jwtToken = token.getJwtToken();
      return converted;
    }
  }

  @Data
  private static class MemberLoginRequest {

    private String memberName;

    private String password;

    public MemberLoginParam convert() {
      MemberLoginParam param = MemberLoginParam.builder()
                                               .memberName(memberName)
                                               .password(password)
                                               .build();
      return param;
    }
  }
}

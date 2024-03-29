package asthux.EBFF.controller;

import asthux.EBFF.domain.member.Member;
import asthux.EBFF.domain.token.Token;
import asthux.EBFF.enums.ReturnCode;
import asthux.EBFF.param.MemberLoginParam;
import asthux.EBFF.provider.JwtProvider;
import asthux.EBFF.repository.RedisTokenRepository;
import asthux.EBFF.response.ApiResponse;
import asthux.EBFF.service.AuthService;
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

  private final AuthService authService;

  @Autowired
  private final JwtProvider jwtProvider;

  private final RedisTokenRepository tokenRepository;

  @PostMapping("/login")
  public ApiResponse<?> login(@RequestBody MemberLoginRequest request) {
    MemberLoginParam param = request.convert();
    Member member = authService.verifyLogin(param);
    Token token = jwtProvider.createToken(member);
    tokenRepository.save(token);
    return ApiResponse.of(TokenItem.of(token));
  }

  @DeleteMapping("/logout")
  public ApiResponse<?> logout() {
    String stringToken = jwtProvider.getToken();
    jwtProvider.verifyToken(stringToken);
    Token token = Token.builder()
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

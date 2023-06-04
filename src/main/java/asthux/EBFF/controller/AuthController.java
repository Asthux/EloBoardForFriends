package asthux.EBFF.controller;

import asthux.EBFF.enums.ReturnCode;
import asthux.EBFF.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
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

  @PostMapping("/login")
  public ApiResponse<?> login() {
    // id, password에 해당하는 유저가 있는지 확인

    // 있으면 JWT 토큰 생성

    // redis에 토큰 저장 ( 만료시간 X )

    // 토큰을 FE에 응답
    return ApiResponse.of(ReturnCode.SUCCESS);
  }

  @PostMapping("/logout")
  public ApiResponse<?> logout() {
    // redis에 저장된 토큰 삭제
    return ApiResponse.of(ReturnCode.SUCCESS);
  }
}

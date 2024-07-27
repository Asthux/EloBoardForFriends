package asthux.EBFF.interceptor;

import asthux.EBFF.provider.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
@Component
@RequiredArgsConstructor
public class LoginCheckInterceptor implements HandlerInterceptor {

  private final JwtProvider jwtProvider;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    if (request.getMethod().equals("GET")) {
      return true;
    }
    String jwt = request.getHeader("X-ACCESS-TOKEN");
    jwtProvider.verifyToken(jwt);
    return true;
  }
}

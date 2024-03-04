package asthux.EBFF.config;

import asthux.EBFF.argumentresolver.LoginMemberArgumentResolver;
import asthux.EBFF.interceptor.LoginCheckInterceptor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

  private final LoginCheckInterceptor loginCheckInterceptor;

  private final LoginMemberArgumentResolver loginMemberArgumentResolver;

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(loginMemberArgumentResolver);
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(loginCheckInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/api/login", "/api/logout", "/api/members");
  }
}

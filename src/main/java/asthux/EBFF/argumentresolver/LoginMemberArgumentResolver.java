package asthux.EBFF.argumentresolver;

import asthux.EBFF.domain.member.Member;
import asthux.EBFF.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

  private final JwtProvider jwtProvider;

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
    boolean hasMemberType = Member.class.isAssignableFrom(parameter.getParameterType());

    return hasLoginAnnotation && hasMemberType;
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    return jwtProvider.getMember();
  }
}

package asthux.EBFF.controller;

import asthux.EBFF.exception.EbffException;
import asthux.EBFF.response.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {
  @ExceptionHandler(EbffException.class)
  public ApiResponse<?> handleEbffException(EbffException e) {
    return ApiResponse.of(e.getReturnCode());
  }
}

package asthux.EBFF.exception;

import asthux.EBFF.enums.ReturnCode;
import lombok.Getter;

@Getter
public class EbffException extends RuntimeException{
  private ReturnCode returnCode;
  private String returnMessage;

  public EbffException(ReturnCode returnCode) {
    this.returnCode = returnCode;
    this.returnMessage = returnCode.getReturnMessage();
  }
}

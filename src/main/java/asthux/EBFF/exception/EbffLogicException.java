package asthux.EBFF.exception;

import asthux.EBFF.enums.ReturnCode;

public class EbffLogicException extends EbffException{
  public EbffLogicException(ReturnCode returnCode) {
    super(returnCode);
  }
}

package asthux.EBFF.exception;

import asthux.EBFF.enums.ReturnCode;

public class EbffRequestException extends EbffException {
  public EbffRequestException(ReturnCode returnCode) {
    super(returnCode);
  }
}

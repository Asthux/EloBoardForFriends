package asthux.EBFF.validator;

import asthux.EBFF.enums.ReturnCode;
import asthux.EBFF.exception.EbffRequestException;

public class PageLimitSizeValidator {
  public static void validateSize(int page, int limit, int maxLimitSize) {
    if (page < 0 || limit <= 0 || limit > maxLimitSize) {
      throw new EbffRequestException(ReturnCode.WRONG_PARAMETER);
    }
  }
}

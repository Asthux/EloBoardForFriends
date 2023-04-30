package asthux.EBFF.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReturnCode {
  SUCCESS("0000", "Success"),

  WRONG_PARAMETER("4000", "wrong parameter" ),
  NOT_FOUND_ENTITY("4001", "Not found entity");

  private String returnCode;
  private String returnMessage;
}

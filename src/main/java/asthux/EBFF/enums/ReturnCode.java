package asthux.EBFF.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReturnCode {
  SUCCESS("0000", "Success"),

  WRONG_PARAMETER("4000", "wrong parameter"),
  NOT_FOUND_ENTITY("4001", "Not found entity"),
  ALREADY_EXIST("4002", "Already exist"),
  WRONG_PASSWORD("4003", "wrong password"),
  NOT_AUTHORIZED("4004", "Not authorized");

  private String returnCode;
  private String returnMessage;
}

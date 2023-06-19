package asthux.EBFF.param;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GameRecordUpdateParam {

  private Long mapId;

  private String ruleSet;

  private PlayerInfoUpdateParam winner;

  private PlayerInfoUpdateParam loser;
}

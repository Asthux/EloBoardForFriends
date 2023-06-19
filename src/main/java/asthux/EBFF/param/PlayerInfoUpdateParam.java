package asthux.EBFF.param;

import asthux.EBFF.domain.game.Race;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PlayerInfoUpdateParam {

  private String playerName;

  private Race race;

  private String gameResult;
}

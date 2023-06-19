package asthux.EBFF.param;

import asthux.EBFF.domain.game.GameRecord;
import asthux.EBFF.domain.game.PlayerInfo;
import asthux.EBFF.domain.game.Race;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PlayerInfoCreateParam {

  private String playerName;

  private Race race;

  private String gameResult;

  public PlayerInfo toEntity(GameRecord gameRecord) {
    PlayerInfo playerInfo = PlayerInfo.builder()
                                      .gameRecord(gameRecord)
                                      .playerName(playerName)
                                      .race(race)
                                      .gameResult(gameResult)
                                      .build();
    return playerInfo;
  }
}

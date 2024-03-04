package asthux.EBFF.param;

import asthux.EBFF.domain.game.GameRecord;
import asthux.EBFF.domain.map.Map;
import asthux.EBFF.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GameRecordCreateParam {

  private Member member;

  private Long mapId;

  private String ruleSet;

  private PlayerInfoCreateParam winner;

  private PlayerInfoCreateParam loser;

  public GameRecord toEntity(Map map) {
    GameRecord gameRecord = GameRecord.builder()
                                      .map(map)
                                      .ruleSet(this.ruleSet)
                                      .build();
    return gameRecord;
  }
}

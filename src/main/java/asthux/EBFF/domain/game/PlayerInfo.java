package asthux.EBFF.domain.game;

import asthux.EBFF.domain.DateTimeEntity;
import asthux.EBFF.enums.GameResult;
import asthux.EBFF.enums.Race;
import asthux.EBFF.param.PlayerInfoUpdateParam;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "game_player_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class PlayerInfo extends DateTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long playerInfoId;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "game_record_id")
  private GameRecord gameRecord;

  private String playerName;

  @Enumerated(EnumType.STRING)
  private Race race;

  @Enumerated(EnumType.STRING)
  private GameResult gameResult;

  private LocalDateTime deletedAt;

  public void update(PlayerInfoUpdateParam param) {
    this.playerName = param.getPlayerName();
    this.race = param.getRace();
    this.gameResult = param.getGameResult();
  }

  public void delete() {
    this.deletedAt = LocalDateTime.now();
  }
}

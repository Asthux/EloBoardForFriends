package asthux.EBFF.domain.game;

import asthux.EBFF.domain.DateTimeEntity;
import asthux.EBFF.domain.map.Map;
import asthux.EBFF.domain.member.Member;
import asthux.EBFF.param.GameRecordUpdateParam;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "game_record")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class GameRecord extends DateTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long gameRecordId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member; // 전적 등록자

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "map_id")
  private Map map;

  private String ruleSet; //경기방식

  private LocalDateTime deletedAt;

  public void update(Map map, GameRecordUpdateParam param) {
    this.map = map;
    this.ruleSet = param.getRuleSet();
  }

  public void delete() {
    this.deletedAt = LocalDateTime.now();
  }
}

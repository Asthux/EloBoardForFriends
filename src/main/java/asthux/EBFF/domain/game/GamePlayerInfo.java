package asthux.EBFF.domain.game;

import asthux.EBFF.domain.DateTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "game_player_info")
public class GamePlayerInfo extends DateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerInfoId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "game_record_id")
    private GameRecord gameRecord;

    private String player;

    @Enumerated(EnumType.STRING)
    private Race race;

    private String gameResult;
}

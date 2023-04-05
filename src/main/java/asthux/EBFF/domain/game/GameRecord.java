package asthux.EBFF.domain.game;

import asthux.EBFF.domain.DateTimeEntity;
import asthux.EBFF.domain.map.Map;
import asthux.EBFF.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "game_record")
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

    private String gameRecord; //경기방식
}

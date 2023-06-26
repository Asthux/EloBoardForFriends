package asthux.EBFF.repository;

import asthux.EBFF.domain.game.GameRecord;
import asthux.EBFF.domain.game.PlayerInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerInfoRepository extends JpaRepository<PlayerInfo, Long> {

  PlayerInfo findByGameRecordAndPlayerName(GameRecord gameRecord, String playerName);

  PlayerInfo findByGameRecordAndPlayerNameNot(GameRecord gameRecord, String playerName);

  List<PlayerInfo> findByGameRecord(GameRecord gameRecord);
}

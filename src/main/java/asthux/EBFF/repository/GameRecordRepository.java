package asthux.EBFF.repository;

import asthux.EBFF.domain.game.GameRecord;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GameRecordRepository extends JpaRepository<GameRecord, Long> {

  @Query("select gameRecord from PlayerInfo playerInfo inner join playerInfo.gameRecord gameRecord where playerName = :playerName")
  List<GameRecord> findGameRecordByPlayerName(@Param("playerName") String playerName);
}

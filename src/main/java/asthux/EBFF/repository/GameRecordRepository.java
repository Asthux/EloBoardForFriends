package asthux.EBFF.repository;

import asthux.EBFF.domain.game.GameRecord;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GameRecordRepository extends JpaRepository<GameRecord, Long> {

  @Query("select gr from GameRecord gr inner join PlayerInfo gpi where gpi.playerName = :playerName AND gr.gameRecordId = gpi.gameRecord.gameRecordId")
  List<GameRecord> findGameRecordByPlayerName(@Param("playerName") String playerName);
}

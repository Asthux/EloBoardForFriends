package asthux.EBFF.service;

import asthux.EBFF.domain.game.GameRecord;
import asthux.EBFF.domain.game.PlayerInfo;
import asthux.EBFF.param.PlayerInfoCreateParam;
import asthux.EBFF.param.PlayerInfoUpdateParam;
import asthux.EBFF.repository.PlayerInfoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerInfoService {

  private final PlayerInfoRepository playerInfoRepository;

  public PlayerInfo findPlayerInfo(GameRecord record, String playerName) {
    PlayerInfo player = playerInfoRepository.findByGameRecordAndPlayerName(record, playerName);
    return player;
  }

  public PlayerInfo findOpponentPlayerInfo(GameRecord record, String playerName) {
    PlayerInfo opponentPlayer = playerInfoRepository.findByGameRecordAndPlayerNameNot(record, playerName);
    return opponentPlayer;
  }

  public void save(GameRecord gameRecord, PlayerInfoCreateParam winnerParam, PlayerInfoCreateParam loserParam) {
    PlayerInfo winner = winnerParam.toEntity(gameRecord);
    PlayerInfo loser = loserParam.toEntity(gameRecord);

    playerInfoRepository.save(winner);
    playerInfoRepository.save(loser);
  }

  public void update(GameRecord gameRecord, PlayerInfoUpdateParam winnerParam, PlayerInfoUpdateParam loserParam) {
    List<PlayerInfo> players = playerInfoRepository.findByGameRecord(gameRecord);

    PlayerInfo winner = null;
    PlayerInfo loser = null;

    for (PlayerInfo player : players) {
      if ("win".equals(player.getGameResult())) {
        winner = PlayerInfo.builder()
                           .playerName(player.getPlayerName())
                           .race(player.getRace())
                           .gameResult(player.getGameResult())
                           .build();
      } else if ("lose".equals(player.getGameResult())) {
        loser = PlayerInfo.builder()
                          .playerName(player.getPlayerName())
                          .race(player.getRace())
                          .gameResult(player.getGameResult())
                          .build();
      }
    }

    winner.update(winnerParam);
    loser.update(loserParam);
  }

  public void delete(GameRecord gameRecord) {
    List<PlayerInfo> players = playerInfoRepository.findByGameRecord(gameRecord);

    for (PlayerInfo player : players) {
      player.delete();
    }
  }
}

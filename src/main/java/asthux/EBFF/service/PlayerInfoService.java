package asthux.EBFF.service;

import asthux.EBFF.domain.game.GameRecord;
import asthux.EBFF.domain.game.PlayerInfo;
import asthux.EBFF.enums.GameResult;
import asthux.EBFF.param.PlayerInfoCreateParam;
import asthux.EBFF.param.PlayerInfoUpdateParam;
import asthux.EBFF.repository.PlayerInfoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PlayerInfoService {

  private final PlayerInfoRepository playerInfoRepository;

  @Transactional(readOnly = true)
  public PlayerInfo findPlayerInfo(GameRecord record, String playerName) {
    return playerInfoRepository.findByGameRecordAndPlayerName(record, playerName);
  }

  @Transactional(readOnly = true)
  public PlayerInfo findOpponentPlayerInfo(GameRecord record, String playerName) {
    return playerInfoRepository.findByGameRecordAndPlayerNameNot(record, playerName);
  }

  public void save(GameRecord gameRecord, PlayerInfoCreateParam param) {
    PlayerInfo player = param.toEntity(gameRecord);

    playerInfoRepository.save(player);
  }

  public void update(GameRecord gameRecord, PlayerInfoUpdateParam param) {
    List<PlayerInfo> players = playerInfoRepository.findByGameRecord(gameRecord);

    PlayerInfo playerInfo = null;

    if (GameResult.VICTORY.equals(param.getGameResult())) {
      for (PlayerInfo player : players) {
        if (GameResult.VICTORY.equals(player.getGameResult())) {
          playerInfo = PlayerInfo.builder()
                                 .playerName(player.getPlayerName())
                                 .race(player.getRace())
                                 .gameResult(player.getGameResult())
                                 .build();
          break;
        }
      }
    } else if (GameResult.DEFEAT.equals(param.getGameResult())) {
      for (PlayerInfo player : players) {
        if (GameResult.DEFEAT.equals(player.getGameResult())) {
          playerInfo = PlayerInfo.builder()
                                 .playerName(player.getPlayerName())
                                 .race(player.getRace())
                                 .gameResult(player.getGameResult())
                                 .build();
          break;
        }
      }
    }
    playerInfo.update(param);
  }

  public void delete(GameRecord gameRecord) {
    List<PlayerInfo> players = playerInfoRepository.findByGameRecord(gameRecord);

    for (PlayerInfo player : players) {
      player.delete();
    }
  }
}

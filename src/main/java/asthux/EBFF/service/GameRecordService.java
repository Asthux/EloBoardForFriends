package asthux.EBFF.service;

import asthux.EBFF.domain.game.GameRecord;
import asthux.EBFF.domain.map.Map;
import asthux.EBFF.enums.ReturnCode;
import asthux.EBFF.exception.EbffLogicException;
import asthux.EBFF.param.GameRecordCreateParam;
import asthux.EBFF.param.GameRecordUpdateParam;
import asthux.EBFF.repository.GameRecordRepository;
import asthux.EBFF.repository.MapRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameRecordService {

  private final MapRepository mapRepository;

  private final GameRecordRepository gameRecordRepository;

  private final PlayerInfoService playerInfoService;

  public List<GameRecord> getRecords(String playerName) {
    List<GameRecord> gameRecords = gameRecordRepository.findGameRecordByPlayerName(playerName);

    return gameRecords;
  }

  public void save(GameRecordCreateParam param) {
    Map map = mapRepository.findById(param.getMapId())
                           .orElseThrow(() -> new EbffLogicException(ReturnCode.NOT_FOUND_ENTITY));

    GameRecord gameRecord = param.toEntity(map);

    playerInfoService.save(gameRecord, param.getWinner(), param.getLoser());

    gameRecordRepository.save(gameRecord);
  }

  public void remove(Long id) {
    GameRecord gameRecord = gameRecordRepository.findById(id)
                                                .orElseThrow(() -> new EbffLogicException(ReturnCode.NOT_FOUND_ENTITY));

    playerInfoService.delete(gameRecord);

    gameRecord.delete();
  }

  public void update(Long id, GameRecordUpdateParam param) {
    GameRecord gameRecord = gameRecordRepository.findById(id)
                                                .orElseThrow(() -> new EbffLogicException(ReturnCode.NOT_FOUND_ENTITY));

    playerInfoService.update(gameRecord, param.getWinner(), param.getLoser());

    Map map = mapRepository.findById(param.getMapId())
                           .orElseThrow(() -> new EbffLogicException(ReturnCode.NOT_FOUND_ENTITY));

    gameRecord.update(map, param);
  }
}

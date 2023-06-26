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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GameRecordService {

  private final MapRepository mapRepository;

  private final GameRecordRepository gameRecordRepository;

  private final PlayerInfoService playerInfoService;

  public List<GameRecord> getRecords(String playerName) {
    return gameRecordRepository.findGameRecordByPlayerName(playerName);
  }

  @Transactional
  public void save(GameRecordCreateParam param) {
    Map map = mapRepository.findById(param.getMapId())
                           .orElseThrow(() -> new EbffLogicException(ReturnCode.NOT_FOUND_ENTITY));

    GameRecord gameRecord = param.toEntity(map);

    playerInfoService.save(gameRecord, param.getWinner());
    playerInfoService.save(gameRecord, param.getLoser());

    gameRecordRepository.save(gameRecord);
  }

  @Transactional
  public void remove(Long id) {
    GameRecord gameRecord = gameRecordRepository.findById(id)
                                                .orElseThrow(() -> new EbffLogicException(ReturnCode.NOT_FOUND_ENTITY));

    playerInfoService.delete(gameRecord);

    gameRecord.delete();
  }

  @Transactional
  public void update(GameRecordUpdateParam param) {
    GameRecord gameRecord = gameRecordRepository.findById(param.getId())
                                                .orElseThrow(() -> new EbffLogicException(ReturnCode.NOT_FOUND_ENTITY));


    Map map = mapRepository.findById(param.getMapId())
                           .orElseThrow(() -> new EbffLogicException(ReturnCode.NOT_FOUND_ENTITY));

    gameRecord.update(map, param);

    playerInfoService.update(gameRecord, param.getWinner());
    playerInfoService.update(gameRecord, param.getLoser());
  }
}

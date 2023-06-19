package asthux.EBFF.controller;

import asthux.EBFF.domain.game.GameRecord;
import asthux.EBFF.domain.game.PlayerInfo;
import asthux.EBFF.domain.game.Race;
import asthux.EBFF.domain.member.Member;
import asthux.EBFF.enums.ReturnCode;
import asthux.EBFF.param.GameRecordCreateParam;
import asthux.EBFF.param.GameRecordUpdateParam;
import asthux.EBFF.param.PlayerInfoCreateParam;
import asthux.EBFF.param.PlayerInfoUpdateParam;
import asthux.EBFF.response.ApiResponse;
import asthux.EBFF.response.EbffPage;
import asthux.EBFF.service.GameRecordService;
import asthux.EBFF.service.PlayerInfoService;
import asthux.EBFF.validator.PageLimitSizeValidator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game_records")
@RequiredArgsConstructor
@Transactional
public class GameController {

  private final GameRecordService gameService;

  private final PlayerInfoService playerInfoService;

  @GetMapping
  public ApiResponse<?> getRecords(@RequestParam("player") String playerName, RecordGetRequest request) {
    PageLimitSizeValidator.validateSize(request.getPage(), request.getLimit(), 100);
    Pageable pageable = PageRequest.of(request.getPage(), request.getLimit());

    List<RecordItem> converted = new ArrayList<>();

    List<GameRecord> records = gameService.getRecords(playerName);
    for (GameRecord record : records) {
      PlayerInfo gamePlayerInfo = playerInfoService.findPlayerInfo(record, playerName);
      PlayerInfo opponentGamePlayerInfo = playerInfoService.findOpponentPlayerInfo(record, playerName);
      converted.add(RecordItem.of(record, gamePlayerInfo, opponentGamePlayerInfo));
    }

    Page<RecordItem> recordItems = new PageImpl<>(converted, pageable, converted.size());

    return ApiResponse.of(EbffPage.of(recordItems));
  }

  @PostMapping
  public ApiResponse<?> create(@RequestBody GameCreateRequest request) {
    GameRecordCreateParam param = request.convert();
    gameService.save(param);
    return ApiResponse.of(ReturnCode.SUCCESS);
  }

  @DeleteMapping("/{id}")
  public ApiResponse<?> delete(@PathVariable("id") Long id) {
    gameService.remove(id);
    return ApiResponse.of(ReturnCode.SUCCESS);
  }

  @PatchMapping("/{id}")
  public ApiResponse<?> update(@PathVariable("id") Long id,
                               @RequestBody GameUpdateRequest request) {
    GameRecordUpdateParam param = request.convert();
    gameService.update(id, param);
    return ApiResponse.of(ReturnCode.SUCCESS);
  }

  @Data
  private static class RecordGetRequest {

    private int page = 0;
    private int limit = 10;
  }

  @Data
  private static class RecordItem {

    private String ruleSet;

    private String mapName;

    private Member member; //전적 등록자

    private String playerName; //검색한 플레이어 게임 아이디

    private Race playerRace; //검색한 플레이어 종족

    private String playerGameResult; //검색한 플레이어 승,패 여부

    private String opponentPlayerName; //상대 게임 아이디

    private Race opponentRace; //상대 종족

    private static RecordItem of(GameRecord gameRecord, PlayerInfo player, PlayerInfo opponent) {
      RecordItem converted = new GameController.RecordItem();
      converted.ruleSet = gameRecord.getRuleSet();
      converted.mapName = gameRecord.getMap().getMapName();
      converted.member = gameRecord.getMember();
      converted.playerRace = player.getRace();
      converted.playerName = player.getPlayerName();
      converted.playerGameResult = player.getGameResult();
      converted.opponentRace = opponent.getRace();
      converted.opponentPlayerName = opponent.getPlayerName();
      return converted;
    }
  }

  @Data
  private static class GameCreateRequest {

    private Long mapId;

    private String ruleSet;

    private PlayerInfoCreateRequest winner;

    private PlayerInfoCreateRequest loser;

    static class PlayerInfoCreateRequest {

      @JsonProperty("playerName")
      private String playerName;

      @JsonProperty("race")
      private Race race;

      @JsonProperty("gameResult")
      private String gameResult;

      public PlayerInfoCreateParam convert(PlayerInfoCreateRequest player) {
        PlayerInfoCreateParam param = PlayerInfoCreateParam.builder()
                                                           .playerName(player.playerName)
                                                           .race(player.race)
                                                           .gameResult(player.gameResult)
                                                           .build();
        return param;
      }
    }

    public GameRecordCreateParam convert() {
      PlayerInfoCreateParam winner = this.winner.convert(this.winner);
      PlayerInfoCreateParam loser = this.loser.convert(this.loser);

      GameRecordCreateParam param = GameRecordCreateParam.builder()
                                                         .mapId(mapId)
                                                         .ruleSet(ruleSet)
                                                         .winner(winner)
                                                         .loser(loser)
                                                         .build();
      return param;
    }
  }

  @Data
  private static class GameUpdateRequest {

    private Long mapId;

    private String ruleSet;

    private PlayerInfoUpdateRequest winner;

    private PlayerInfoUpdateRequest loser;

    static class PlayerInfoUpdateRequest {

      @JsonProperty("playerName")
      private String playerName;

      @JsonProperty("race")
      private Race race;

      @JsonProperty("gameResult")
      private String gameResult;

      public PlayerInfoUpdateParam convert(PlayerInfoUpdateRequest player) {
        PlayerInfoUpdateParam param = PlayerInfoUpdateParam.builder()
                                                           .playerName(player.playerName)
                                                           .race(player.race)
                                                           .gameResult(player.gameResult)
                                                           .build();
        return param;
      }
    }

    public GameRecordUpdateParam convert() {
      PlayerInfoUpdateParam winner = this.winner.convert(this.winner);
      PlayerInfoUpdateParam loser = this.loser.convert(this.loser);

      GameRecordUpdateParam param = GameRecordUpdateParam.builder()
                                                         .mapId(mapId)
                                                         .ruleSet(ruleSet)
                                                         .winner(winner)
                                                         .loser(loser)
                                                         .build();
      return param;
    }
  }
}

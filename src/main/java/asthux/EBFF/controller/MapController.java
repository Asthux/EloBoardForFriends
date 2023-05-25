package asthux.EBFF.controller;

import asthux.EBFF.domain.map.Map;
import asthux.EBFF.enums.ReturnCode;
import asthux.EBFF.param.MapCreateParam;
import asthux.EBFF.param.MapUpdateParam;
import asthux.EBFF.response.ApiResponse;
import asthux.EBFF.response.EbffPage;
import asthux.EBFF.service.MapService;
import asthux.EBFF.validator.PageLimitSizeValidator;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/maps")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MapController {

  private final MapService mapService;

  @GetMapping
  public ApiResponse<?> getMaps(MapGetRequest request) {
    PageLimitSizeValidator.validateSize(request.getPage(), request.getLimit(), 100);
    Pageable pageable = PageRequest.of(request.getPage(), request.getLimit());

    Page<MapItem> maps = mapService.getMap(pageable)
                                   .map(MapItem::of);
    return ApiResponse.of(EbffPage.of(maps));
  }

  @Transactional
  @PostMapping
  public ApiResponse<?> create(@RequestBody MapCreateRequest request) {
    MapCreateParam param = request.convert();
    mapService.save(param);
    return ApiResponse.of(ReturnCode.SUCCESS);
  }

  @GetMapping("/{id}")
  public ApiResponse<?> getMap(@PathVariable("id") Long id) {
    Map map = mapService.getMapById(id);
    return ApiResponse.of(MapItem.of(map));
  }

  @Transactional
  @DeleteMapping("/{id}")
  public ApiResponse<?> delete(@PathVariable("id") Long id) {
    mapService.remove(id);
    return ApiResponse.of(ReturnCode.SUCCESS);
  }

  @Transactional
  @PatchMapping("/{id}")
  public ApiResponse<?> update(@PathVariable("id") Long id, @RequestBody MapUpdateRequest request) {
    MapUpdateParam param = request.convert();
    mapService.update(id, param);
    return ApiResponse.of(ReturnCode.SUCCESS);
  }

  @Data
  private static class MapGetRequest {

    private int page = 0;
    private int limit = 30;
  }

  @Data
  private static class MapItem {

    private String mapName;

    private static MapController.MapItem of(Map map) {
      MapController.MapItem converted = new MapController.MapItem();
      converted.mapName = map.getMapName();
      return converted;
    }
  }

  @Data
  private static class MapCreateRequest {

    private String mapName;

    public MapCreateParam convert() {
      MapCreateParam param = MapCreateParam.builder()
                                           .mapName(mapName)
                                           .build();
      return param;
    }
  }

  @Data
  private static class MapUpdateRequest {

    private String mapName;

    public MapUpdateParam convert() {
      MapUpdateParam param = MapUpdateParam.builder()
                                           .mapName(mapName)
                                           .build();
      return param;
    }
  }
}

package asthux.EBFF.param;

import asthux.EBFF.domain.map.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MapCreateParam {

  private String mapName;

  public Map toEntity() {
    Map map = Map.builder()
                 .mapName(mapName)
                 .build();
    return map;
  }
}

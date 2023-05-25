package asthux.EBFF.domain.map;

import asthux.EBFF.domain.DateTimeEntity;
import asthux.EBFF.param.MapUpdateParam;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Map extends DateTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long mapId;

  private String mapName;

  public void update(MapUpdateParam param) {
    this.mapName = param.getMapName();
  }
}

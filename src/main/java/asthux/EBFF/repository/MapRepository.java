package asthux.EBFF.repository;

import asthux.EBFF.domain.map.Map;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MapRepository extends JpaRepository<Map, Long> {

  Page<Map> findAll(Pageable pageable);

  Optional<Map> findByMapName(String mapName);
}

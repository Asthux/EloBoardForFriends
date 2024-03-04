package asthux.EBFF.service;

import asthux.EBFF.domain.map.Map;
import asthux.EBFF.enums.ReturnCode;
import asthux.EBFF.exception.EbffLogicException;
import asthux.EBFF.param.MapCreateParam;
import asthux.EBFF.param.MapUpdateParam;
import asthux.EBFF.repository.MapRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MapService {

  private final MapRepository mapRepository;

  @Transactional(readOnly = true)
  public Page<Map> getMap(Pageable pageable) {
    return mapRepository.findAll(pageable);
  }

  public void save(MapCreateParam param) {
    validateDuplicateMap(param);

    Map map = param.toEntity();

    mapRepository.save(map);
  }

  private void validateDuplicateMap(MapCreateParam param) {
    Optional<Map> mapExist = mapRepository.findByMapName(param.getMapName());
    if (mapExist.isPresent()) {
      throw new EbffLogicException(ReturnCode.ALREADY_EXIST);
    }
  }

  public Map getMapById(Long id) {
    return mapRepository.findById(id)
                        .orElseThrow(() -> new EbffLogicException(ReturnCode.NOT_FOUND_ENTITY));
  }

  public void remove(Long id) {
    Map map = mapRepository.findById(id)
                           .orElseThrow(() -> new EbffLogicException(ReturnCode.NOT_FOUND_ENTITY));

    mapRepository.delete(map);
  }

  public void update(Long id, MapUpdateParam param) {
    Map map = mapRepository.findById(id)
                           .orElseThrow(() -> new EbffLogicException(ReturnCode.NOT_FOUND_ENTITY));
    map.update(param);
  }
}

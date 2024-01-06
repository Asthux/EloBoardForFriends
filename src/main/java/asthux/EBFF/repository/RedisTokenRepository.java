package asthux.EBFF.repository;

import asthux.EBFF.domain.token.Token;
import org.springframework.data.repository.CrudRepository;

public interface RedisTokenRepository extends CrudRepository<Token, Long> {

}

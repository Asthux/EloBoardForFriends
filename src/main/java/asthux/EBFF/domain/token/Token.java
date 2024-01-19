package asthux.EBFF.domain.token;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "Token", timeToLive = 60 * 60)
@Getter
@Builder
public class Token implements Serializable {

  @Id
  private String jwtToken;
}

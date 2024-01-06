package asthux.EBFF.domain.token;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "Token")
@Getter
@Builder
public class Token implements Serializable {

  @Id
  private Long memberId;

  private String jwtToken;

}

package asthux.EBFF.domain.member;

import asthux.EBFF.domain.DateTimeEntity;
import asthux.EBFF.enums.Role;
import asthux.EBFF.param.MemberUpdateParam;
import jakarta.persistence.*;
import java.time.LocalDateTime;
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
public class Member extends DateTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long memberId;

  private String memberName; // id

  private String password;

  private String name;

  private String nickName;

  @Enumerated(EnumType.STRING)
  private Role role;

  private LocalDateTime deletedAt;

  public void update(MemberUpdateParam param) {
    this.password = param.getPassword();
    this.name = param.getName();
    this.nickName = param.getNickName();
  }

  public void delete() {
    this.deletedAt = LocalDateTime.now();
  }
}

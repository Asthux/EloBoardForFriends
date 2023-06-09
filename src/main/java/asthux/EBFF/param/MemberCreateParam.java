package asthux.EBFF.param;

import asthux.EBFF.domain.member.Member;
import asthux.EBFF.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MemberCreateParam {

  private String memberName; // id

  private String password;

  private String name;

  private String nickName;

  private Role role;

  public Member toEntity() {
    Member member = Member.builder()
                          .memberName(memberName)
                          .password(password)
                          .name(name)
                          .nickName(nickName)
                          .role(Role.USER)
                          .build();
    return member;
  }
}
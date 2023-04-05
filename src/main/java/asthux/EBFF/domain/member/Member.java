package asthux.EBFF.domain.member;

import asthux.EBFF.domain.DateTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Member extends DateTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String memberName; // id

    private String password;

    private String name;

    private String nickName;

    @Enumerated(EnumType.STRING)
    private Role role;

}

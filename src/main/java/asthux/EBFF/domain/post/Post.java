package asthux.EBFF.domain.post;

import asthux.EBFF.domain.DateTimeEntity;
import asthux.EBFF.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Post extends DateTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    private String content;


}

package asthux.EBFF.domain.post;

import asthux.EBFF.domain.DateTimeEntity;
import asthux.EBFF.domain.member.Member;
import asthux.EBFF.param.PostUpdateParam;
import jakarta.persistence.*;
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
public class Post extends DateTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long postId;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "member_id")
  private Member member;

  private String title;

  private String content;

  public void update(PostUpdateParam param) {
    this.title = param.getTitle();
    this.content = param.getContent();
  }
}

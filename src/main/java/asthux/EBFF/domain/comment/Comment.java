package asthux.EBFF.domain.comment;

import asthux.EBFF.domain.DateTimeEntity;
import asthux.EBFF.domain.member.Member;
import asthux.EBFF.domain.post.Post;
import asthux.EBFF.param.CommentUpdateParam;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Comment extends DateTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long commentId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "post_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Post post;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  private Member member;

  private String content;

  private LocalDateTime deletedAt;

  public void update(CommentUpdateParam param) {
    this.content = param.getContent();
  }

  public void delete() {
    this.deletedAt = LocalDateTime.now();
  }
}

package asthux.EBFF.param;

import asthux.EBFF.domain.comment.Comment;
import asthux.EBFF.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class CommentCreateParam {

  private Post post;

  private String content;

  public Comment toEntity() {
    Comment comment = Comment.builder()
                             .post(post)
                             .content(content)
                             .build();
    return comment;
  }
}


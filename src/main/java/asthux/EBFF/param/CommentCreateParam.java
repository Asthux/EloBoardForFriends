package asthux.EBFF.param;

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
}


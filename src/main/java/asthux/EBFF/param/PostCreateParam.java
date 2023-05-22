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
public class PostCreateParam {

  private String title;

  private String content;

  public Post toEntity() {
    Post post = Post.builder()
                    .title(title)
                    .content(content)
                    .build();
    return post;
  }
}

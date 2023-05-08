package asthux.EBFF.service;

import asthux.EBFF.domain.post.Post;
import asthux.EBFF.enums.ReturnCode;
import asthux.EBFF.exception.EbffLogicException;
import asthux.EBFF.param.PostCreateParam;
import asthux.EBFF.param.PostUpdateParam;
import asthux.EBFF.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;

  public Page<Post> getPost(Pageable pageable) {
    return postRepository.findAll(pageable);
  }

  public void save(PostCreateParam param) {
    Post post = Post.builder()
                    .title(param.getTitle())
                    .content(param.getContent())
                    .build();
    postRepository.save(post);
  }

  public Post getPostById(Long id) {
    return postRepository.findById(id)
                         .orElseThrow(() -> new EbffLogicException(ReturnCode.NOT_FOUND_ENTITY));
  }

  public void remove(Long id) {
    Post post = postRepository.findById(id)
                              .orElseThrow(() -> new EbffLogicException(ReturnCode.NOT_FOUND_ENTITY));

    postRepository.delete(post);
  }

  public void update(Long id, PostUpdateParam param) {
    Post post = postRepository.findById(id)
                              .orElseThrow(() -> new EbffLogicException(ReturnCode.NOT_FOUND_ENTITY));
    post.update(param);
  }
}

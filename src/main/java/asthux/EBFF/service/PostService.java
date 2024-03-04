package asthux.EBFF.service;

import asthux.EBFF.domain.member.Member;
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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

  private final PostRepository postRepository;

  private final AuthService authService;

  @Transactional(readOnly = true)
  public Page<Post> getPosts(Pageable pageable) {
    return postRepository.findAll(pageable);
  }

  public void save(PostCreateParam param) {
    Post post = param.toEntity();
    postRepository.save(post);
  }

  @Transactional(readOnly = true)
  public Post getPost(Long id) {
    return postRepository.findById(id)
                         .orElseThrow(() -> new EbffLogicException(ReturnCode.NOT_FOUND_ENTITY));
  }

  public void remove(Long loginMemberId, Long id) {
    Post post = postRepository.findById(id)
                              .orElseThrow(() -> new EbffLogicException(ReturnCode.NOT_FOUND_ENTITY));
    authService.isAuthorityMatched(post.getPostId(), loginMemberId);
    postRepository.delete(post);
  }

  public void update(Long loginMemberId, Long id, PostUpdateParam param) {
    Post post = postRepository.findById(id)
                              .orElseThrow(() -> new EbffLogicException(ReturnCode.NOT_FOUND_ENTITY));
    authService.isAuthorityMatched(post.getPostId(), loginMemberId);
    post.update(param);
  }
}

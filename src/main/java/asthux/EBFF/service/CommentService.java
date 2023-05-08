package asthux.EBFF.service;

import asthux.EBFF.domain.comment.Comment;
import asthux.EBFF.domain.post.Post;
import asthux.EBFF.enums.ReturnCode;
import asthux.EBFF.exception.EbffLogicException;
import asthux.EBFF.param.CommentCreateParam;
import asthux.EBFF.param.CommentUpdateParam;
import asthux.EBFF.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;

  public Page<Comment> getComment(Post post, Pageable pageable) {
    return commentRepository.findByPost(post, pageable);
  }

  public void save(CommentCreateParam param) {
    Comment comment = Comment.builder()
                             .post(param.getPost())
                             .content(param.getContent())
                             .build();
    commentRepository.save(comment);
  }

  public void remove(Long id) {
    Comment comment = commentRepository.findById(id)
                                       .orElseThrow(() -> new EbffLogicException(ReturnCode.NOT_FOUND_ENTITY));

    commentRepository.delete(comment);
  }

  public void update(Long id, CommentUpdateParam param) {
    Comment comment = commentRepository.findById(id)
                                       .orElseThrow(() -> new EbffLogicException(ReturnCode.NOT_FOUND_ENTITY));
    comment.update(param);
  }
}

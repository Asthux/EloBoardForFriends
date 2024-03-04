package asthux.EBFF.controller;

import asthux.EBFF.argumentresolver.Login;
import asthux.EBFF.domain.member.Member;
import asthux.EBFF.domain.post.Post;
import asthux.EBFF.enums.ReturnCode;
import asthux.EBFF.param.CommentCreateParam;
import asthux.EBFF.param.CommentUpdateParam;
import asthux.EBFF.response.ApiResponse;
import asthux.EBFF.service.CommentService;
import asthux.EBFF.service.PostService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;
  private final PostService postService;

  @PostMapping("/{id}")
  public ApiResponse<?> create(@Login Member loginMember, @PathVariable("id") Long id,
                               @RequestBody CommentCreateRequest request) {
    Post post = postService.getPost(id);
    CommentCreateParam param = request.convert(post, loginMember);
    commentService.save(param);
    return ApiResponse.of(ReturnCode.SUCCESS);
  }

  @DeleteMapping("/{id}")
  public ApiResponse<?> delete(@Login Member loginMember, @PathVariable("id") Long id) {
    commentService.remove(loginMember.getMemberId(), id);
    return ApiResponse.of(ReturnCode.SUCCESS);
  }

  @PatchMapping("/{id}")
  public ApiResponse<?> update(@Login Member loginMember, @PathVariable("id") Long id,
                               @RequestBody CommentUpdateRequest request) {
    CommentUpdateParam param = request.convert();
    commentService.update(loginMember.getMemberId(), id, param);
    return ApiResponse.of(ReturnCode.SUCCESS);
  }


  @Data
  private static class CommentCreateRequest {

    private String content;

    public CommentCreateParam convert(Post post, Member loginMember) {
      CommentCreateParam param = CommentCreateParam.builder()
                                                   .member(loginMember)
                                                   .post(post)
                                                   .content(content)
                                                   .build();
      return param;
    }
  }

  @Data
  private static class CommentUpdateRequest {

    private String content;

    public CommentUpdateParam convert() {
      CommentUpdateParam param = CommentUpdateParam.builder()
                                                   .content(content)
                                                   .build();
      return param;
    }
  }
}

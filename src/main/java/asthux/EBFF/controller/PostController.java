package asthux.EBFF.controller;

import asthux.EBFF.argumentresolver.Login;
import asthux.EBFF.domain.comment.Comment;
import asthux.EBFF.domain.member.Member;
import asthux.EBFF.domain.post.Post;
import asthux.EBFF.enums.ReturnCode;
import asthux.EBFF.param.PostCreateParam;
import asthux.EBFF.param.PostUpdateParam;
import asthux.EBFF.response.ApiResponse;
import asthux.EBFF.response.EbffPage;
import asthux.EBFF.service.CommentService;
import asthux.EBFF.service.PostService;
import asthux.EBFF.validator.PageLimitSizeValidator;

import lombok.Data;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;

  private final CommentService commentService;

  @GetMapping
  public ApiResponse<?> getPosts(PostGetRequest request) {
    PageLimitSizeValidator.validateSize(request.getPage(), request.getLimit(), 100);
    Pageable pageable = PageRequest.of(request.getPage(), request.getLimit());

    Page<PostItem> posts = postService.getPosts(pageable)
                                      .map(PostItem::of);
    return ApiResponse.of(EbffPage.of(posts));

    // stream to for loop
    // List<PostItem> postItems = new ArrayList<>();
    // for (Post post : posts) {
    //   postItems.add(PostItem.of(post));
    // }
    // return ApiResponse.of(postItems);
  }

  @GetMapping("/{id}/comments")
  public ApiResponse<?> getComments(@PathVariable("id") Long id, CommentGetRequest request) {
    PageLimitSizeValidator.validateSize(request.getPage(), request.getLimit(), 100);
    Pageable pageable = PageRequest.of(request.getPage(), request.getLimit());

    Post post = postService.getPost(id);
    Page<CommentItem> comments = commentService.getComment(post, pageable)
                                               .map(CommentItem::of);
    return ApiResponse.of(EbffPage.of(comments));
  }

  @PostMapping
  public ApiResponse<?> create(@Login Member loginMember, @RequestBody PostCreateRequest request) {
    PostCreateParam param = request.convert(loginMember);
    postService.save(param);
    return ApiResponse.of(ReturnCode.SUCCESS);
  }

  @GetMapping("/{id}")
  public ApiResponse<?> getPost(@PathVariable("id") Long id) {
    Post post = postService.getPost(id);
    return ApiResponse.of(PostItem.of(post));
  }

  @DeleteMapping("/{id}")
  public ApiResponse<?> delete(@Login Member loginMember, @PathVariable("id") Long id) {
    postService.remove(loginMember.getMemberId(), id);
    return ApiResponse.of(ReturnCode.SUCCESS);
  }

  @PatchMapping("/{id}")
  public ApiResponse<?> update(@Login Member loginMember, @PathVariable("id") Long id,
                               @RequestBody PostUpdateRequest request) {
    PostUpdateParam param = request.convert();
    postService.update(loginMember.getMemberId(), id, param);
    return ApiResponse.of(ReturnCode.SUCCESS);
  }

  @Data
  private static class PostGetRequest {

    private int page = 0;
    private int limit = 20;
  }

  @Data
  private static class PostItem {

    private Long memberId;

    private String memberName;

    private String title;
    private String content;

    private static PostItem of(Post post) {
      PostItem converted = new PostItem();
      converted.memberId = post.getMember().getMemberId();
      converted.memberName = post.getMember().getMemberName();
      converted.title = post.getTitle();
      converted.content = post.getContent();
      return converted;
    }
  }

  @Data
  private static class PostCreateRequest {

    private String title;
    private String content;

    public PostCreateParam convert(Member loginMember) {
      PostCreateParam param = PostCreateParam.builder()
                                             .member(loginMember)
                                             .title(title)
                                             .content(content)
                                             .build();
      return param;
    }
  }

  @Data
  private static class PostUpdateRequest {

    private String title;
    private String content;

    public PostUpdateParam convert() {
      PostUpdateParam param = PostUpdateParam.builder()
                                             .title(title)
                                             .content(content)
                                             .build();
      return param;
    }
  }

  @Data
  private static class CommentGetRequest {

    private int page = 0;
    private int limit = 10;
  }

  @Data
  private static class CommentItem {

    private String content;

    private static PostController.CommentItem of(Comment comment) {
      PostController.CommentItem converted = new PostController.CommentItem();
      converted.content = comment.getContent();
      return converted;
    }
  }
}

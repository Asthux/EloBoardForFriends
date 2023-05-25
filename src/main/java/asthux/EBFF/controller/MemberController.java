package asthux.EBFF.controller;

import asthux.EBFF.domain.member.Member;
import asthux.EBFF.enums.ReturnCode;
import asthux.EBFF.param.MemberCreateParam;
import asthux.EBFF.param.MemberUpdateParam;
import asthux.EBFF.response.ApiResponse;
import asthux.EBFF.response.EbffPage;
import asthux.EBFF.service.MemberService;
import asthux.EBFF.validator.PageLimitSizeValidator;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberController {

  private final MemberService memberService;

  @GetMapping
  public ApiResponse<?> getMembers(MemberController.MemberGetRequest request) {
    PageLimitSizeValidator.validateSize(request.getPage(), request.getLimit(), 100);
    Pageable pageable = PageRequest.of(request.getPage(), request.getLimit());

    Page<MemberController.MemberItem> members = memberService.getMembers(pageable)
                                                             .map(MemberController.MemberItem::of);
    return ApiResponse.of(EbffPage.of(members));
  }

  @Transactional
  @PostMapping
  public ApiResponse<?> create(@RequestBody MemberController.MemberCreateRequest request) {
    MemberCreateParam param = request.convert();
    memberService.save(param);
    return ApiResponse.of(ReturnCode.SUCCESS);
  }

  @GetMapping("/{id}")
  public ApiResponse<?> getMember(@PathVariable("id") Long id) {
    Member member = memberService.getMember(id);
    return ApiResponse.of(MemberController.MemberItem.of(member));
  }

  @Transactional
  @DeleteMapping("/{id}")
  public ApiResponse<?> delete(@PathVariable("id") Long id) {
    memberService.remove(id);
    return ApiResponse.of(ReturnCode.SUCCESS);
  }

  @Transactional
  @PatchMapping("/{id}")
  public ApiResponse<?> update(@PathVariable("id") Long id, @RequestBody MemberController.MemberUpdateRequest request) {
    MemberUpdateParam param = request.convert();
    memberService.update(id, param);
    return ApiResponse.of(ReturnCode.SUCCESS);
  }

  @Data
  private static class MemberGetRequest {

    private int page = 0;
    private int limit = 30;
  }

  @Data
  private static class MemberItem {

    private String memberName; // id

    private String password;

    private String name;

    private String nickName;

    private static MemberController.MemberItem of(Member member) {
      MemberController.MemberItem converted = new MemberController.MemberItem();
      converted.memberName = member.getMemberName();
      converted.password = member.getPassword();
      converted.name = member.getName();
      converted.nickName = member.getNickName();
      return converted;
    }
  }

  @Data
  private static class MemberCreateRequest {

    private String memberName; // id

    private String password;

    private String name;

    private String nickName;

    public MemberCreateParam convert() {
      MemberCreateParam param = MemberCreateParam.builder()
                                                 .memberName(memberName)
                                                 .password(password)
                                                 .name(name)
                                                 .nickName(nickName)
                                                 .build();
      return param;
    }
  }

  @Data
  private static class MemberUpdateRequest {

    private String password;

    private String name;

    private String nickName;

    public MemberUpdateParam convert() {
      MemberUpdateParam param = MemberUpdateParam.builder()
                                                 .password(password)
                                                 .name(name)
                                                 .nickName(nickName)
                                                 .build();
      return param;
    }
  }
}
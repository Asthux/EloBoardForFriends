package asthux.EBFF.service;

import asthux.EBFF.domain.member.Member;
import asthux.EBFF.enums.ReturnCode;
import asthux.EBFF.exception.EbffLogicException;
import asthux.EBFF.param.MemberCreateParam;
import asthux.EBFF.param.MemberUpdateParam;
import asthux.EBFF.repository.MemberRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  public Page<Member> getMembers(Pageable pageable) {
    return memberRepository.findAll(pageable);
  }

  public void save(MemberCreateParam param) {
    validateDuplicateMember(param);

    Member member = param.toEntity();

    memberRepository.save(member);
  }

  private void validateDuplicateMember(MemberCreateParam param) {
    Optional<Member> memberExist = memberRepository.findByMemberName(param.getMemberName());
    if (memberExist.isPresent()) {
      throw new EbffLogicException(ReturnCode.ALREADY_EXIST);
    }
  }

  public Member getMember(Long id) {
    return memberRepository.findById(id)
                           .orElseThrow(() -> new EbffLogicException(ReturnCode.NOT_FOUND_ENTITY));
  }

  public void remove(Long id) {
    Member member = memberRepository.findById(id)
                                    .orElseThrow(() -> new EbffLogicException(ReturnCode.NOT_FOUND_ENTITY));

    memberRepository.delete(member);
  }

  public void update(Long id, MemberUpdateParam param) {
    Member member = memberRepository.findById(id)
                                    .orElseThrow(() -> new EbffLogicException(ReturnCode.NOT_FOUND_ENTITY));
    member.update(param);
  }
}
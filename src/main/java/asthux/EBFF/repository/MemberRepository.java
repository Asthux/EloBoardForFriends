package asthux.EBFF.repository;

import asthux.EBFF.domain.member.Member;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Page<Member> findAll(Pageable pageable);

  Optional<Member> findByMemberName(String memberName);
}
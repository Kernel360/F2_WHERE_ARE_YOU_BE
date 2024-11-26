package org.badminton.infrastructure.clubmember;

import java.time.LocalDateTime;
import java.util.List;

import org.badminton.domain.domain.clubmember.entity.ClubMemberBanRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BannedClubMemberRepository extends JpaRepository<ClubMemberBanRecord, Long> {
	List<ClubMemberBanRecord> findAllByEndDateBeforeAndIsActiveTrue(LocalDateTime now);
}

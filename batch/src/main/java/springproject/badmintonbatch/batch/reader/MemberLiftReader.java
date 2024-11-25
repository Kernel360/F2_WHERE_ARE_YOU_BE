package springproject.badmintonbatch.batch.reader;

import java.time.LocalDateTime;
import java.util.List;

import org.badminton.domain.domain.clubmember.entity.ClubMemberBanRecord;
import org.badminton.infrastructure.clubmember.BannedClubMemberRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberLiftReader implements ItemReader<ClubMemberBanRecord> {

	private final BannedClubMemberRepository bannedClubMemberRepository;

	private List<ClubMemberBanRecord> clubMembersToLift;
	private int currentIndex = 0;

	@Override
	public ClubMemberBanRecord read() {
		if (clubMembersToLift == null) {
			LocalDateTime currentTime = LocalDateTime.now();
			clubMembersToLift = bannedClubMemberRepository.findAllByEndDateBeforeAndIsActiveTrue(currentTime);

		}
		if (currentIndex >= clubMembersToLift.size()) {
			clubMembersToLift = null;
			currentIndex = 0;
			return null;
		}
		return clubMembersToLift.get(currentIndex++);
	}
}

package springproject.badmintonbatch.batch.processor;

import org.badminton.domain.domain.clubmember.entity.ClubMemberBanRecord;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberLiftProcessor implements ItemProcessor<ClubMemberBanRecord, ClubMemberBanRecord> {

	@Override
	public ClubMemberBanRecord process(ClubMemberBanRecord item) {

		item.liftClubMember();

		return null;
	}
}

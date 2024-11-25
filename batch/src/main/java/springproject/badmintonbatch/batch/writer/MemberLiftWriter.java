package springproject.badmintonbatch.batch.writer;

import org.badminton.domain.domain.clubmember.entity.ClubMemberBanRecord;
import org.badminton.infrastructure.clubmember.BannedClubMemberRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberLiftWriter implements ItemWriter<ClubMemberBanRecord> {

	private final BannedClubMemberRepository bannedClubMemberRepository;

	@Override
	public void write(Chunk<? extends ClubMemberBanRecord> chunk) {
		bannedClubMemberRepository.saveAll(chunk);
	}
}

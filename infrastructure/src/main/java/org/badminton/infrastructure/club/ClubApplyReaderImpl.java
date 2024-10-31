package org.badminton.infrastructure.club;

import org.badminton.domain.domain.club.ClubApplyReader;
import org.badminton.domain.domain.club.entity.ClubApply;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClubApplyReaderImpl implements ClubApplyReader {
	private final ClubApplyRepository clubApplyRepository;

	@Override
	public ClubApply getClubApply(Long clubApplyId) {
		return clubApplyRepository.findByClubApplyId(clubApplyId);
	}

	@Override
	public boolean IsExistClubApply(String clubToken, String memberToken) {
		return clubApplyRepository.existsByClubClubTokenAndMemberMemberTokenAndStatus(clubToken, memberToken,
			ClubApply.ApplyStatus.PENDING);
	}
}

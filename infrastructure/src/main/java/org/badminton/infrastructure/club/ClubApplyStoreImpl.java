package org.badminton.infrastructure.club;

import org.badminton.domain.domain.club.ClubApplyStore;
import org.badminton.domain.domain.club.entity.ClubApply;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClubApplyStoreImpl implements ClubApplyStore {
	private final ClubApplyRepository clubApplyRepository;

	@Override
	public ClubApply store(ClubApply clubApply) {
		return clubApplyRepository.save(clubApply);
	}
}

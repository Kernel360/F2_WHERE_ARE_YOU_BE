package org.badminton.domain.infrastructures.club;

import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.club.ClubStore;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClubStoreImpl implements ClubStore {
	private final ClubRepository clubRepository;

	@Override
	public Club store(Club club) {
		return clubRepository.save(club);
	}
}

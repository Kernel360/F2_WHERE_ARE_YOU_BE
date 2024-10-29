package org.badminton.infrastructure.club;

import org.badminton.domain.common.exception.club.ClubNotExistException;
import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.club.ClubReader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClubReaderImpl implements ClubReader {
	private final ClubRepository clubRepository;

	@Override
	public Page<Club> readAllClubs(Pageable pageable) {
		return clubRepository.findAllByIsClubDeletedIsFalse(pageable);
	}

	@Override
	public Page<Club> keywordSearch(String keyword, Pageable pageable) {
		return clubRepository.findAllByClubNameContainingIgnoreCaseAndIsClubDeletedIsFalse(keyword, pageable);
	}

	@Override
	public Club readClub(String clubToken) {
		return clubRepository.findByClubToken(clubToken).orElseThrow(() -> new ClubNotExistException(clubToken));
	}
}

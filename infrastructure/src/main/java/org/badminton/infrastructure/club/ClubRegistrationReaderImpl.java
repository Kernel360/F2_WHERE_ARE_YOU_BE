package org.badminton.infrastructure.club;

import java.util.List;

import org.badminton.domain.domain.club.ClubRegistrationReader;
import org.badminton.domain.domain.club.entity.ClubRegistration;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClubRegistrationReaderImpl implements ClubRegistrationReader {
	private final ClubRegistrationRepository clubRegistrationRepository;

	@Override
	public List<ClubRegistration> readAllClubRegistration(String clubToken) {
		return clubRegistrationRepository.findAllByClubClubToken(clubToken);
	}

	@Override
	public ClubRegistration readClubRegistration(String clubToken, String memberToken) {
		return clubRegistrationRepository.findByClubClubTokenAndMemberMemberToken(clubToken, memberToken);
	}
}

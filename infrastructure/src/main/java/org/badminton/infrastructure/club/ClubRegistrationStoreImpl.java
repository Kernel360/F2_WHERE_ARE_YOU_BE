package org.badminton.infrastructure.club;

import org.badminton.domain.domain.club.ClubRegistrationStore;
import org.badminton.domain.domain.club.entity.ClubRegistration;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClubRegistrationStoreImpl implements ClubRegistrationStore {
	private final ClubRegistrationRepository clubRegistrationRepository;

	@Override
	public ClubRegistration store(ClubRegistration clubRegistration) {
		return clubRegistrationRepository.save(clubRegistration);
	}
}

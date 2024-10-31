package org.badminton.domain.domain.club;

import java.util.List;

import org.badminton.domain.domain.club.entity.ClubRegistration;

public interface ClubRegistrationReader {

	List<ClubRegistration> readAllClubRegistration(String clubToken);

	ClubRegistration readClubRegistration(String clubToken, String memberToken);
	
}

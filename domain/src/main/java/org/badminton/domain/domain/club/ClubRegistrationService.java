package org.badminton.domain.domain.club;

import org.badminton.domain.domain.club.command.ApproveRegistrationCommand;
import org.badminton.domain.domain.club.command.RegistrationClubCommand;
import org.badminton.domain.domain.club.command.RejectRegistrationCommand;

public interface ClubRegistrationService {
	void approveRegistration(ApproveRegistrationCommand approveRegistrationCommand);

	void rejectRegistration(RejectRegistrationCommand rejectRegistrationCommand);

	void registrationClub(RegistrationClubCommand registrationClubCommand);

}

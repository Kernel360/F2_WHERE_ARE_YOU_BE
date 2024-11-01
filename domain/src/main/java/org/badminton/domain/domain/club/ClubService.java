package org.badminton.domain.domain.club;

import java.util.List;

import org.badminton.domain.domain.club.command.ClubCreateCommand;
import org.badminton.domain.domain.club.command.ClubUpdateCommand;
import org.badminton.domain.domain.club.entity.ClubApply;
import org.badminton.domain.domain.club.info.ClubCardInfo;
import org.badminton.domain.domain.club.info.ClubCreateInfo;
import org.badminton.domain.domain.club.info.ClubDeleteInfo;
import org.badminton.domain.domain.club.info.ClubSummaryInfo;
import org.badminton.domain.domain.club.info.ClubUpdateInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClubService {
	Page<ClubCardInfo> readAllClubs(Pageable pageable);

	ClubSummaryInfo readClub(String clubToken);

	Page<ClubCardInfo> searchClubs(String keyword, Pageable pageable);

	ClubCreateInfo createClub(ClubCreateCommand clubCreateCommand);

	ClubUpdateInfo updateClub(ClubUpdateCommand clubUpdateCommand, String clubToken);

	ClubDeleteInfo deleteClub(String clubToken);

	ClubCardInfo readClubById(Long clubId);

	List<ClubApply> readClubApplicants(String clubToken);
}

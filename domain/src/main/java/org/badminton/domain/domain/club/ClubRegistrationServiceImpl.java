package org.badminton.domain.domain.club;

import org.badminton.domain.domain.club.command.ApproveRegistrationCommand;
import org.badminton.domain.domain.club.command.RegistrationClubCommand;
import org.badminton.domain.domain.club.command.RejectRegistrationCommand;
import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.club.entity.ClubRegistration;
import org.badminton.domain.domain.clubmember.ClubMemberStore;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.member.entity.Member;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClubRegistrationServiceImpl implements ClubRegistrationService {

	private final ClubMemberStore clubMemberStore;
	private final ClubRegistrationReader clubRegistrationReader;
	private final ClubRegistrationStore clubRegistrationStore;

	@Override
	public void approveRegistration(ApproveRegistrationCommand approveRegistrationCommand) {
		String clubToken = approveRegistrationCommand.clubToken();
		String memberToken = approveRegistrationCommand.memberToken();
		ClubRegistration clubRegistration = clubRegistrationReader.readClubRegistration(clubToken, memberToken);
		clubRegistration.approvedClubMember();
		clubRegistrationStore.store(clubRegistration);

		Club club = clubRegistration.getClub();
		Member member = clubRegistration.getMember();

		ClubMember clubMember = new ClubMember(club, member, ClubMember.ClubMemberRole.ROLE_USER);

		clubMemberStore.store(clubMember);
	}

	@Override
	public void rejectRegistration(RejectRegistrationCommand rejectRegistrationCommand) {

		String clubToken = rejectRegistrationCommand.clubToken();
		String memberToken = rejectRegistrationCommand.memberToken();

		ClubRegistration clubRegistration = clubRegistrationReader.readClubRegistration(clubToken, memberToken);
		clubRegistration.rejectedClubMember();
		clubRegistrationStore.store(clubRegistration);
	}

	@Override
	public void registrationClub(RegistrationClubCommand registrationClubCommand) {
		// TODO : 클럽 가입 요청 로직 작성
	}
}

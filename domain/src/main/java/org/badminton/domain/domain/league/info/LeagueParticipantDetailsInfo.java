package org.badminton.domain.domain.league.info;

import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.league.entity.LeagueParticipant;

public record LeagueParticipantDetailsInfo(

	String memberToken,
	String name,
	String profileImage,
	ClubMember.ClubMemberRole role

) {
	public static LeagueParticipantDetailsInfo from(LeagueParticipant leagueParticipant) {
		return new LeagueParticipantDetailsInfo(
			leagueParticipant.getMember().getMemberToken(),
			leagueParticipant.getMember().getName(),
			leagueParticipant.getMember().getProfileImage(),
			leagueParticipant.getClubMember().getRole()
		);
	}
}

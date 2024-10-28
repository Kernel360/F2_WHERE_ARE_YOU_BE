package org.badminton.domain.domain.club;

import java.util.Map;

import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.club.info.ClubCardInfo;
import org.badminton.domain.domain.member.entity.Member;
import org.springframework.data.domain.Page;

import lombok.Builder;
import lombok.ToString;

@Builder
@ToString
public class ClubPage {
	private final Page<Club> club;

	public Page<ClubCardInfo> clubToPageCardInfo() {

		return this.club.map(club -> {

			Map<Member.MemberTier, Long> tierCounts = club.getClubMemberCountByTier();

			return ClubCardInfo.clubEntityToClubsCardResponse(club, tierCounts);

		});

	}

}


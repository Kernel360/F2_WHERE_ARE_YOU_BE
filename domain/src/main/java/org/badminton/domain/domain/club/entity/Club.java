package org.badminton.domain.domain.club.entity;

import static org.badminton.domain.domain.club.info.ClubSummaryInfo.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.badminton.domain.common.AbstractBaseTime;
import org.badminton.domain.common.TokenGenerator;
import org.badminton.domain.domain.club.command.ClubUpdateCommand;
import org.badminton.domain.domain.club.info.ClubCreateInfo;
import org.badminton.domain.domain.clubmember.entity.ClubMember;
import org.badminton.domain.domain.member.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "club")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Club extends AbstractBaseTime {
	public static final String CLUB_PREFIX = "club_";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long clubId;

	@Column(name = "clubToken")
	private String clubToken;

	private String clubName;

	@Column(columnDefinition = "TEXT", length = 1000)
	private String clubDescription;

	private String clubImage;

	private boolean isClubDeleted;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "club")
	private List<ClubMember> clubMembers = new ArrayList<>();

	public Club(String clubName, String clubDescription, String clubImage) {
		super();
		this.clubToken = TokenGenerator.randomCharacterWithPrefix(CLUB_PREFIX);
		this.clubName = clubName;
		this.clubDescription = clubDescription;
		this.clubImage = clubImage;
		isClubDeleted = false;
	}

	public Club(ClubCreateInfo clubCreateInfo) {
		this.clubId = clubCreateInfo.clubId();
		this.clubDescription = clubCreateInfo.clubDescription();
		this.clubName = clubCreateInfo.clubName();
		this.clubImage = clubCreateInfo.clubImage();
		this.clubToken = clubCreateInfo.clubToken();
		this.isClubDeleted = false;
	}

	public Club(Long clubId, String clubToken, String clubName, String clubDescription, String clubImage,
		List<ClubMember> clubMembers) {
		this.clubId = clubId;
		this.clubToken = clubToken;
		this.clubName = clubName;
		this.clubDescription = clubDescription;
		this.clubImage = clubImage;
		this.clubMembers = clubMembers;
		isClubDeleted = false;
	}

	public void updateClub(ClubUpdateCommand clubUpdateCommand) {
		this.clubName = clubUpdateCommand.clubName();
		this.clubDescription = clubUpdateCommand.clubDescription();
		this.clubImage = clubUpdateCommand.clubImage();
	}

	public Map<Member.MemberTier, Long> getClubMemberCountByTier() {
		return getMemberTierLongMap(clubMembers);
	}

	public void doWithdrawal() {
		this.isClubDeleted = true;
	}
}

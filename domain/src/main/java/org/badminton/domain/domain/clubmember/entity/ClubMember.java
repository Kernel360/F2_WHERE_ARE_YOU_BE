package org.badminton.domain.domain.clubmember.entity;

import java.util.ArrayList;
import java.util.List;

import org.badminton.domain.common.AbstractBaseTime;
import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.member.entity.Member;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "club_member")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ClubMember extends AbstractBaseTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long clubMemberId;

	private boolean deleted;

	private boolean banned;

	@Enumerated(EnumType.STRING)
	private ClubMemberRole role;

	@ManyToOne
	@JoinColumn(name = "clubId")
	private Club club;

	@ManyToOne
	@JoinColumn(name = "memberId")
	private Member member;

	@OneToMany(mappedBy = "clubMember", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ClubMemberBanRecord> banHistory = new ArrayList<>();

	public ClubMember(Club club, Member member, ClubMemberRole role) {
		this.club = club;
		this.member = member;
		this.role = role;
		this.deleted = false;
		this.banned = false;
	}

	public void updateClubMemberRole(ClubMemberRole role) {
		this.role = role;
	}

	public void withdrawal() {
		this.deleted = true;
	}

	public void expel() {
		this.deleted = true;
		this.banned = true;
	}

	public void addBanRecord(ClubMemberBanRecord clubMemberBanRecord) {
		this.banHistory.add(clubMemberBanRecord);
		this.banned = true;
	}

	@Getter
	public enum BannedType {
		THREE_DAYS("3일 정지", 3),
		SEVEN_DAYS("7일 정지", 7),
		TWO_WEEKS("14일 정지", 14),
		PERMANENT("영구 정지", Integer.MAX_VALUE);

		private final String description;
		private final int days;

		BannedType(String description, int days) {
			this.description = description;
			this.days = days;
		}

		public boolean isPermanent() {
			return this == PERMANENT;
		}
	}

	@Getter
	public enum ClubMemberRole {
		ROLE_OWNER("owner"),
		ROLE_MANAGER("manager"),
		ROLE_USER("user");

		private final String description;

		ClubMemberRole(String description) {
			this.description = description;
		}
	}

}

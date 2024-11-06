package org.badminton.domain.domain.member.entity;

import java.time.LocalDateTime;

import org.badminton.domain.common.AbstractBaseTime;
import org.badminton.domain.common.TokenGenerator;
import org.badminton.domain.domain.league.entity.LeagueRecord;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@NoArgsConstructor
@Getter
public class Member extends AbstractBaseTime {

	public static final String MEMBER_PREFIX = "me_";
	@Id
	@Column(name = "memberId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String memberToken;

	private String providerId;

	private String email;

	private String name;

	private String authorization;

	private String profileImage;

	private boolean isDeleted = false;

	private LocalDateTime lastConnectionAt;

	private String refreshToken;

	@Enumerated(EnumType.STRING)
	private MemberTier tier;
	@OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private LeagueRecord leagueRecord;

	public Member(String email, String name, String providerId, String profileImage,
		MemberAuthorization authorization) {
		this.email = email;
		this.memberToken = TokenGenerator.randomCharacterWithPrefix(MEMBER_PREFIX);
		this.name = name;
		this.providerId = providerId;
		this.authorization = authorization.name();
		this.profileImage = profileImage;
		this.tier = MemberTier.BRONZE;
		this.leagueRecord = new LeagueRecord(this);
	}

	public void updateRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public void updateMember(String imageUrl, String newName) {
		this.profileImage = imageUrl;
		this.name = newName;
	}

	// TODO: 회원 탈퇴 시 동호회에서도 탈퇴해야 함
	public void doWithdrawal() {
		this.isDeleted = true;
		this.updateLastConnection();
	}

	public void updateLastConnection() {
		this.lastConnectionAt = LocalDateTime.now();
	}

	public void reactivateMember() {
		this.isDeleted = false;
	}

	public boolean isMemberDeleted() {
		return this.isDeleted;
	}

	public void updateTier(MemberTier tier) {
		this.tier = tier;
	}

	@Getter
	public enum MemberTier {
		GOLD,
		SILVER,
		BRONZE;
	}

}

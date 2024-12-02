package org.badminton.domain.domain.club.entity;

import org.badminton.domain.common.AbstractBaseTime;
import org.badminton.domain.domain.member.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "club_apply")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubApply extends AbstractBaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long clubApplyId;

	@Enumerated(EnumType.STRING)
	private ApplyStatus status;

	@Column(columnDefinition = "TEXT")
	private String applyReason;

	@ManyToOne
	@JoinColumn(name = "clubId")
	private Club club;

	@ManyToOne
	@JoinColumn(name = "memberId")
	private Member member;

	public ClubApply(Club club, Member member, String applyReason) {
		this.club = club;
		this.member = member;
		this.applyReason = applyReason;
		this.status = ApplyStatus.PENDING;
	}

	public void rejectClubMember() {
		this.status = ApplyStatus.REJECTED;
	}

	public void approveClubMember() {
		this.status = ApplyStatus.APPROVED;
	}

	@Getter
	public enum ApplyStatus {
		APPROVED("승인"),
		PENDING("승인 대기중"),
		REJECTED("거부");

		private final String description;

		ApplyStatus(String description) {
			this.description = description;
		}
	}
}

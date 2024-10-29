package org.badminton.domain.domain.statistics;

import org.badminton.domain.common.AbstractBaseTime;
import org.badminton.domain.domain.club.entity.Club;
import org.badminton.domain.domain.club.info.ClubCreateInfo;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "club_statistics")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubStatistics extends AbstractBaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long clubStatisticsId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clubId")
	private Club club;

	private int visitedCount;

	private int registrationCount;

	public void increaseVisitedCount() {
		this.visitedCount++;
	}

	public ClubStatistics(ClubCreateInfo clubCreateInfo) {
		this.club = clubCreateInfo.toEntity();
		this.visitedCount = 0;
		this.registrationCount = 0;
	}
}

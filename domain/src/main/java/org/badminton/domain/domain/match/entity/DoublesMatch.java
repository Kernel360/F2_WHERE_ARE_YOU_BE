package org.badminton.domain.domain.match.entity;

import static org.badminton.domain.common.consts.Constants.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.badminton.domain.common.AbstractBaseTime;
import org.badminton.domain.common.enums.MatchResult;
import org.badminton.domain.common.enums.MatchStatus;
import org.badminton.domain.common.enums.SetStatus;
import org.badminton.domain.domain.league.entity.League;
import org.badminton.domain.domain.league.entity.LeagueParticipant;
import org.badminton.domain.domain.league.vo.Team;

import jakarta.persistence.AssociationOverride;
import jakarta.persistence.AssociationOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "doubles_match")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DoublesMatch extends AbstractBaseTime {

	@OneToMany(mappedBy = "doublesMatch", cascade = CascadeType.ALL, orphanRemoval = true)
	List<DoublesSet> doublesSets;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "leagueId")
	private League league;
	@Embedded
	@AssociationOverrides({
		@AssociationOverride(name = "leagueParticipant1", joinColumns = @JoinColumn(name = "team1Participant1Id")),
		@AssociationOverride(name = "leagueParticipant2", joinColumns = @JoinColumn(name = "team1Participant2Id"))
	})
	private Team team1;
	@Embedded
	@AssociationOverrides({
		@AssociationOverride(name = "leagueParticipant1", joinColumns = @JoinColumn(name = "team2Participant1Id")),
		@AssociationOverride(name = "leagueParticipant2", joinColumns = @JoinColumn(name = "team2Participant2Id"))
	})
	private Team team2;
	private int team1WinSetCount;
	private int team2WinSetCount;
	@Enumerated(EnumType.STRING)
	private MatchResult team1MatchResult = MatchResult.NONE;
	@Enumerated(EnumType.STRING)
	private MatchResult team2MatchResult = MatchResult.NONE;
	private int roundNumber;

	@Enumerated(EnumType.STRING)
	private MatchStatus matchStatus = MatchStatus.NOT_STARTED;

	public DoublesMatch(League league, Team team1, Team team2, int roundNumber) {
		this.league = league;
		this.team1 = team1;
		this.team2 = team2;
		this.doublesSets = new ArrayList<>();
		this.team1WinSetCount = INITIAL_WIN_SET_COUNT;
		this.team2WinSetCount = INITIAL_WIN_SET_COUNT;
		this.roundNumber = roundNumber;
	}

	public void addSet(DoublesSet doublesSet) {
		this.doublesSets.add(doublesSet);
	}

	public void team1WinSet() {
		this.team1WinSetCount++;
		if (team1WinSetCount == SETS_REQUIRED_TO_WIN_MATCH) {
			this.team1MatchResult = MatchResult.WIN;
			this.team2MatchResult = MatchResult.LOSE;
			this.matchStatus = MatchStatus.FINISHED;
		}
	}

	public void team2WinSet() {
		this.team2WinSetCount++;
		if (team2WinSetCount == SETS_REQUIRED_TO_WIN_MATCH) {
			this.team2MatchResult = MatchResult.WIN;
			this.team1MatchResult = MatchResult.LOSE;
			this.matchStatus = MatchStatus.FINISHED;
		}
	}

	public void setDrawMatch() {
		this.team1MatchResult = MatchResult.DRAW;
		this.team2MatchResult = MatchResult.DRAW;
		this.matchStatus = MatchStatus.FINISHED;
	}

	public boolean isDrawMatch() {
		return this.team1WinSetCount == this.team2WinSetCount;
	}

	public void defineTeam1(Team winner) {
		this.team1 = winner;
	}

	public void defineTeam2(Team winner) {
		this.team2 = winner;
	}

	public DoublesSet getDoublesSet(int setNumber) {
		return this.doublesSets.get(setNumber - 1);
	}

	public void finishMatch() {
		this.matchStatus = MatchStatus.FINISHED;
	}

	public void startMatchSet(int setNumber) {
		this.matchStatus = MatchStatus.IN_PROGRESS;
		this.getDoublesSet(setNumber).open();
	}

	public Optional<DoublesSet> getSetInProgress() {
		return this.doublesSets.stream()
			.filter(doublesSet -> doublesSet.getSetStatus() == SetStatus.IN_PROGRESS)
			.findFirst();
	}

	public Integer getSetNumberInProgress() {
		if (getSetInProgress().isEmpty()) {
			return null;
		}
		return getSetInProgress().get().getSetNumber();
	}

	public void byeMatch() {
		this.matchStatus = MatchStatus.BYE;
	}

	public Team determineWinner() {
		if (this.getMatchStatus() == MatchStatus.BYE || this.getTeam1MatchResult() == MatchResult.WIN) {
			return this.getTeam1();
		}

		if (this.getTeam2MatchResult() == MatchResult.WIN) {
			return this.getTeam2();
		}
		return Team.emptyWinner();
	}

	public boolean isTeam1Exist() {
		return this.getTeam1() != null;
	}

	public boolean isTeam2Exist() {
		return this.getTeam2() != null;
	}

	public boolean isMatchWinnerDetermined() {
		return this.team1MatchResult == MatchResult.WIN || this.team2MatchResult == MatchResult.WIN;
	}

	public boolean isByeMatch() {
		return this.matchStatus == MatchStatus.BYE && this.isTeam1Exist();
	}

	public void determineWinnerTeam(LeagueParticipant leagueParticipant) {
		if (team1.belongToTeam(leagueParticipant)) {
			// 여기에 해당 참가자가 있으면 team1 lose
			this.team1MatchResult = MatchResult.LOSE;
			this.team2MatchResult = MatchResult.WIN;
			this.matchStatus = MatchStatus.FINISHED;
		}

		if (team2.belongToTeam(leagueParticipant)) {
			// 여기에 해당 참가자가 있으면 team2 lose
			this.team2MatchResult = MatchResult.LOSE;
			this.team1MatchResult = MatchResult.WIN;
			this.matchStatus = MatchStatus.FINISHED;
		}
	}

	public void closeMatchContainsBannedParticipant() {
		this.doublesSets.forEach(set -> set.endSetScore(0, 0));
		this.finishMatch();
	}

	public String[] getAllMemberToken() {
		return new String[] {
			this.getTeam1().getLeagueParticipant1().getMember().getMemberToken(),
			this.getTeam1().getLeagueParticipant2().getMember().getMemberToken(),
			this.getTeam2().getLeagueParticipant1().getMember().getMemberToken(),
			this.getTeam2().getLeagueParticipant2().getMember().getMemberToken()
		};
	}

}

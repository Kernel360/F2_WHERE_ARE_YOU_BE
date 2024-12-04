package org.badminton.api.interfaces.league.controller;

import java.util.List;

import org.badminton.api.interfaces.league.dto.IsLeagueParticipantResponse;
import org.badminton.api.interfaces.league.dto.LeagueByDateResponse;
import org.badminton.api.interfaces.league.dto.LeagueCancelResponse;
import org.badminton.api.interfaces.league.dto.LeagueCreateRequest;
import org.badminton.api.interfaces.league.dto.LeagueCreateResponse;
import org.badminton.api.interfaces.league.dto.LeagueParticipantResponse;
import org.badminton.api.interfaces.league.dto.LeagueParticipationCancelResponse;
import org.badminton.api.interfaces.league.dto.LeagueReadResponse;
import org.badminton.api.interfaces.league.dto.LeagueRecruitingCompleteResponse;
import org.badminton.api.interfaces.league.dto.LeagueUpdateRequest;
import org.badminton.api.interfaces.league.dto.LeagueUpdateResponse;
import org.badminton.domain.domain.league.command.LeagueCreateNoIncludeClubCommand;
import org.badminton.domain.domain.league.command.LeagueUpdateCommand;
import org.badminton.domain.domain.league.info.IsLeagueParticipantInfo;
import org.badminton.domain.domain.league.info.LeagueByDateInfoWithParticipantCountInfo;
import org.badminton.domain.domain.league.info.LeagueCancelInfo;
import org.badminton.domain.domain.league.info.LeagueCreateInfo;
import org.badminton.domain.domain.league.info.LeagueParticipantCancelInfo;
import org.badminton.domain.domain.league.info.LeagueParticipantInfo;
import org.badminton.domain.domain.league.info.LeagueReadInfo;
import org.badminton.domain.domain.league.info.LeagueRecruitingCompleteInfo;
import org.badminton.domain.domain.league.info.LeagueUpdateInfoWithParticipantCountInfo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import jakarta.validation.Valid;

@Mapper(
	componentModel = "spring",
	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
	unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface LeagueDtoMapper {

	LeagueCreateResponse of(LeagueCreateInfo responseInfo);

	LeagueRecruitingCompleteResponse of(LeagueRecruitingCompleteInfo responseInfo);

	LeagueCreateNoIncludeClubCommand of(@Valid LeagueCreateRequest leagueCreateRequest, String leagueToken);

	List<LeagueReadResponse> mapLeagueReadInfoList(List<LeagueReadInfo> responseInfo);

	LeagueUpdateCommand of(@Valid LeagueUpdateRequest leagueUpdateRequest);

	LeagueCancelResponse of(LeagueCancelInfo leagueInfo);

	LeagueParticipantResponse of(LeagueParticipantInfo leagueParticipantInfo);

	LeagueParticipationCancelResponse of(LeagueParticipantCancelInfo result);

	LeagueUpdateResponse of(LeagueUpdateInfoWithParticipantCountInfo leagueUpdateInfo);

	List<LeagueByDateResponse> of(List<LeagueByDateInfoWithParticipantCountInfo> responseInfo);

	IsLeagueParticipantResponse of(IsLeagueParticipantInfo isLeagueParticipantInfo);
}

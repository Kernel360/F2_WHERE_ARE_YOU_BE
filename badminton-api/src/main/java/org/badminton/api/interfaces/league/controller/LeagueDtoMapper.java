// package org.badminton.api.interfaces.league.controller;
//
// import java.util.List;
//
// import org.badminton.api.interfaces.league.dto.LeagueByDateResponse;
// import org.badminton.api.interfaces.league.dto.LeagueCancelResponse;
// import org.badminton.api.interfaces.league.dto.LeagueCreateRequest;
// import org.badminton.api.interfaces.league.dto.LeagueCreateResponse;
// import org.badminton.api.interfaces.league.dto.LeagueDetailsResponse;
// import org.badminton.api.interfaces.league.dto.LeagueReadResponse;
// import org.badminton.api.interfaces.league.dto.LeagueUpdateRequest;
// import org.badminton.api.interfaces.league.dto.LeagueUpdateResponse;
// import org.badminton.domain.domain.league.command.LeagueCreateCommand;
// import org.badminton.domain.domain.league.command.LeagueUpdateCommand;
// import org.badminton.domain.domain.league.info.LeagueByDateInfo;
// import org.badminton.domain.domain.league.info.LeagueCancelInfo;
// import org.badminton.domain.domain.league.info.LeagueCreateInfo;
// import org.badminton.domain.domain.league.info.LeagueDetailsInfo;
// import org.badminton.domain.domain.league.info.LeagueReadInfo;
// import org.badminton.domain.domain.league.info.LeagueUpdateInfo;
// import org.mapstruct.InjectionStrategy;
// import org.mapstruct.Mapper;
// import org.mapstruct.ReportingPolicy;
//
// import jakarta.validation.Valid;
//
// @Mapper(
// 	componentModel = "spring",
// 	injectionStrategy = InjectionStrategy.CONSTRUCTOR,
// 	unmappedTargetPolicy = ReportingPolicy.ERROR
// )
// public interface LeagueDtoMapper {
//
// 	LeagueCreateResponse of(LeagueCreateInfo responseInfo);
//
// 	LeagueCreateCommand of(@Valid LeagueCreateRequest leagueCreateRequest);
//
// 	List<LeagueReadResponse> mapLeagueReadInfoList(List<LeagueReadInfo> responseInfo);
//
// 	List<LeagueByDateResponse> mapLeagueByDateInfoList(List<LeagueByDateInfo> responseInfo);
//
// 	LeagueDetailsResponse of(LeagueDetailsInfo leagueDetailsInfo);
//
// 	LeagueUpdateCommand of(@Valid LeagueUpdateRequest leagueUpdateRequest);
//
// 	LeagueUpdateResponse of(LeagueUpdateInfo leagueUpdateInfo);
//
// 	LeagueCancelResponse of(LeagueCancelInfo leagueInfo);
// }

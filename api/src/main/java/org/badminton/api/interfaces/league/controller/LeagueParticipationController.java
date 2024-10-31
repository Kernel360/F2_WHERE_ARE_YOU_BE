package org.badminton.api.interfaces.league.controller;

import org.badminton.api.application.league.LeagueParticipationFacade;
import org.badminton.api.common.response.CommonResponse;
import org.badminton.api.interfaces.auth.dto.CustomOAuth2Member;
import org.badminton.api.interfaces.league.dto.LeagueParticipantResponse;
import org.badminton.api.interfaces.league.dto.LeagueParticipationCancelResponse;
import org.badminton.domain.domain.league.info.LeagueParticipantCancelInfo;
import org.badminton.domain.domain.league.info.LeagueParticipantInfo;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/clubs/{clubToken}/leagues")
public class LeagueParticipationController {

	private final LeagueParticipationFacade leagueParticipationFacade;
	private final LeagueDtoMapper leagueDtoMapper;

	@Operation(
		summary = "경기 참여 신청",
		description = "가입된 사용자가 경기 참여를 신청합니다.",
		tags = {"league"}
	)
	@PostMapping("{leagueId}/participation")
	public CommonResponse<LeagueParticipantResponse> participateInLeague(
		@PathVariable String clubToken,
		@PathVariable Long leagueId,
		Authentication authentication
	) {
		CustomOAuth2Member member = (CustomOAuth2Member)authentication.getPrincipal();
		String memberToken = member.getMemberToken();
		LeagueParticipantInfo leagueParticipantInfo =
			leagueParticipationFacade.participateInLeague(memberToken, clubToken, leagueId);
		LeagueParticipantResponse response = leagueDtoMapper.of(leagueParticipantInfo);
		return CommonResponse.success(response);
	}

	@Operation(
		summary = "경기 참여 신청 취소",
		description = "경기 참여 신청을 취소합니다.",
		tags = {"league"}
	)
	@DeleteMapping("{leagueId}/participation")
	public CommonResponse<LeagueParticipationCancelResponse> cancelLeagueParticipation(
		@PathVariable String clubToken,
		@PathVariable Long leagueId,
		Authentication authentication
	) {
		CustomOAuth2Member member = (CustomOAuth2Member)authentication.getPrincipal();
		String memberToken = member.getMemberToken();

		LeagueParticipantCancelInfo result = leagueParticipationFacade.cancelLeagueParticipation(clubToken, memberToken,
			leagueId);
		LeagueParticipationCancelResponse response = leagueDtoMapper.of(result);
		return CommonResponse.success(response);
	}

}

// package org.badminton.api.interfaces.league.controller;
//
// import org.badminton.api.interfaces.league.dto.LeagueParticipantResponse;
// import org.badminton.api.interfaces.league.dto.LeagueParticipationCancelResponse;
// import org.badminton.api.service.league.LeagueParticipationService;
// import org.badminton.api.interfaces.oauth.dto.CustomOAuth2Member;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.core.Authentication;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
//
// import io.swagger.v3.oas.annotations.Operation;
// import lombok.RequiredArgsConstructor;
//
// @RequiredArgsConstructor
// @RestController
// @RequestMapping("/v1/clubs/{clubId}/leagues")
// public class LeagueParticipationController {
//
//     private final LeagueParticipationService leagueParticipationService;
//
//     @Operation(
//             summary = "경기 참여 신청",
//             description = "동호회 회원이 경기 일정에 참여 신청을 합니다.",
//             tags = {"league"}
//     )
//     @PostMapping("{leagueId}/participation")
//     public ResponseEntity<LeagueParticipantResponse> participateInLeague(
//             @PathVariable Long clubId,
//             @PathVariable Long leagueId,
//             Authentication authentication
//     ) {
//         CustomOAuth2Member member = (CustomOAuth2Member) authentication.getPrincipal();
//         Long memberId = member.getMemberId();
//         LeagueParticipantResponse leagueParticipantResponse = leagueParticipationService.participateInLeague(clubId,
//                 leagueId, memberId);
//         return ResponseEntity.ok(leagueParticipantResponse);
//     }
//
//     @Operation(
//             summary = "경기 참여 신청 취소",
//             description = "경기 참여 신청을 취소합니다.",
//             tags = {"league"}
//     )
//     @DeleteMapping("{leagueId}/participation")
//     public ResponseEntity<LeagueParticipationCancelResponse> cancelLeagueParticipation(
//             @PathVariable Long clubId,
//             @PathVariable Long leagueId,
//             Authentication authentication
//     ) {
//         CustomOAuth2Member member = (CustomOAuth2Member) authentication.getPrincipal();
//         Long memberId = member.getMemberId();
//         LeagueParticipationCancelResponse leagueParticipationCancelResponse = leagueParticipationService.cancelLeagueParticipation(
//                 clubId, leagueId, memberId);
//         return ResponseEntity.ok(leagueParticipationCancelResponse);
//     }
//
// }

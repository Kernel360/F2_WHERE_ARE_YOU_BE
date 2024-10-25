// package org.badminton.api.interfaces.auth.controller;
//
// import org.badminton.api.application.auth.AuthFacade;
// import org.badminton.api.common.response.CommonResponse;
// import org.badminton.api.interfaces.member.MemberDtoMapper;
// import org.badminton.api.interfaces.member.dto.MemberDeleteResponse;
// import org.badminton.api.interfaces.oauth.dto.CustomOAuth2Member;
// import org.badminton.domain.domain.member.info.MemberDeleteInfo;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.core.annotation.AuthenticationPrincipal;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
//
// import io.swagger.v3.oas.annotations.Operation;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
//
// @RestController
// @RequiredArgsConstructor
// @RequestMapping("v1/members")
// @Slf4j
// public class AuthController {
//
// 	private final AuthFacade authFacade;
// 	private final MemberDtoMapper memberDtoMapper;
//
// 	@Operation(
// 		summary = "회원 탈퇴를 합니다",
// 		description = "멤버 필드의 isDeleted 를 true 로 변경합니다",
// 		tags = {"Member"}
// 	)
// 	@DeleteMapping
// 	public CommonResponse<MemberDeleteResponse> deleteMember(HttpServletResponse response,
// 		@AuthenticationPrincipal CustomOAuth2Member member) {
// 		MemberDeleteInfo deleteResponse = authFacade.deleteMember(member, response);
// 		MemberDeleteResponse memberDeleteResponse = memberDtoMapper.of(deleteResponse);
// 		return CommonResponse.success(memberDeleteResponse);
// 	}
//
// 	@Operation(
// 		summary = "액세스 토큰을 재발급합니다",
// 		description = "리프레시 토큰을 이용해서 액세스 토큰을 재발급합니다",
// 		tags = {"Member"}
// 	)
// 	@PostMapping("/refresh")
// 	public ResponseEntity<String> refreshToken(HttpServletRequest request, HttpServletResponse response) {
// 		return authFacade.refreshToken(request, response);
// 	}
//
// 	@Operation(
// 		summary = "로그아웃을 합니다",
// 		description = "쿠키에서 JWT 토큰을 제거해 로그아웃을 합니다",
// 		tags = {"Member"}
// 	)
// 	@PostMapping("/logout")
// 	public CommonResponse<String> logout(HttpServletResponse response,
// 		@AuthenticationPrincipal CustomOAuth2Member member) {
// 		log.info("Logout request received");
// 		authFacade.logoutMember(member.getMemberToken(), response);
// 		return CommonResponse.success("Logged out successfully");
// 	}
// }

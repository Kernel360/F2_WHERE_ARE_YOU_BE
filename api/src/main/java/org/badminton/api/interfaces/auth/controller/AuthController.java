package org.badminton.api.interfaces.auth.controller;

import org.badminton.api.application.auth.AuthFacade;
import org.badminton.api.common.response.CommonResponse;
import org.badminton.api.interfaces.auth.AuthDtoMapper;
import org.badminton.api.interfaces.auth.dto.CustomOAuth2Member;
import org.badminton.api.interfaces.auth.dto.MemberDeleteResponse;
import org.badminton.domain.domain.auth.info.MemberDeleteInfo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/members")
@Slf4j
public class AuthController {

	private final AuthFacade authFacade;
	private final AuthDtoMapper authDtoMapper;

	@Operation(
		summary = "회원 탈퇴를 합니다",
		description = "멤버 필드의 isDeleted 를 true 로 변경합니다",
		tags = {"Member"}
	)
	@DeleteMapping
	public CommonResponse<MemberDeleteResponse> deleteMember(HttpServletResponse response,
		@AuthenticationPrincipal CustomOAuth2Member member) {

		MemberDeleteInfo memberDeleteInfo = authFacade.deleteMember(member, response);
		MemberDeleteResponse memberDeleteResponse = authDtoMapper.of(memberDeleteInfo);
		return CommonResponse.success(memberDeleteResponse);
	}

	@Operation(
		summary = "로그아웃을 합니다",
		description = "쿠키에서 JWT 토큰을 제거해 로그아웃을 합니다",
		tags = {"Member"}
	)
	@PostMapping("/logout")
	public CommonResponse<String> logout(HttpServletResponse response,
		@AuthenticationPrincipal CustomOAuth2Member member) {

		authFacade.logoutMember(member.getMemberToken(), response);
		return CommonResponse.success("Logout success");

	}
}

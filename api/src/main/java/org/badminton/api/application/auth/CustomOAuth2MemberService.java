package org.badminton.api.application.auth;

import org.badminton.api.interfaces.auth.dto.CustomOAuth2Member;
import org.badminton.api.interfaces.auth.dto.GoogleResponse;
import org.badminton.api.interfaces.auth.dto.KakaoResponse;
import org.badminton.api.interfaces.auth.dto.NaverResponse;
import org.badminton.api.interfaces.auth.dto.OAuthResponse;
import org.badminton.api.interfaces.member.dto.MemberRequest;
import org.badminton.api.interfaces.member.dto.MemberResponse;
import org.badminton.domain.domain.member.MemberStore;
import org.badminton.domain.domain.member.command.MemberCommand;
import org.badminton.domain.domain.member.entity.Member;
import org.badminton.domain.domain.member.entity.MemberAuthorization;
import org.badminton.domain.domain.member.info.MemberInfo;
import org.badminton.infrastructure.member.MemberRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2MemberService extends DefaultOAuth2UserService {

	private final MemberRepository memberRepository;
	private final MemberStore memberStore;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		log.info("CustomOAuth2MemberService is being executed");

		OAuth2User oAuth2User = super.loadUser(userRequest);
		log.info("USer details: {}", oAuth2User);

		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		log.info("registrationId: {}", registrationId);
		OAuthResponse oAuth2Response = null;
		String oAuthAccessToken = userRequest.getAccessToken().getTokenValue();

		switch (registrationId) {
			case "naver" -> oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
			case "google" -> oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
			case "kakao" -> oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
			default -> {
				return null;
			}
		}
		String providerId = oAuth2Response.getProviderId();
		Member member = memberRepository.findByProviderId(providerId).orElse(null);

		if (member == null) {

			MemberRequest memberRequest = new MemberRequest(MemberAuthorization.AUTHORIZATION_USER,
				oAuth2Response.getName(),
				oAuth2Response.getEmail(), providerId, oAuth2Response.getProfileImage());
			MemberCommand memberCommand = memberRequest.of();
			member = memberCommand.toEntity();

		} else if (member.isDeleted()) {
			member.reactivateMember();
		}

		log.info(
			"Member details - ID: {}, Name: {},  Email: {}, MemberToken: {}, ProviderId: {}, ProfileImage: {}, Authorization: {}, IsDeleted: {}, CreatedAt: {}, ModifiedAt: {}",
			member.getId(),
			member.getName(),
			member.getEmail(),
			member.getMemberToken(),
			member.getProviderId(),
			member.getProfileImage(),
			member.getAuthorization(),
			member.isDeleted(),
			member.getCreatedAt(),
			member.getModifiedAt()
		);
		memberStore.store(member);
		MemberInfo memberInfo = MemberInfo.toMemberInfo(member);
		MemberResponse memberResponse = MemberResponse.fromMemberInfo(memberInfo);

		return new CustomOAuth2Member(memberResponse, registrationId, oAuthAccessToken);

	}
}

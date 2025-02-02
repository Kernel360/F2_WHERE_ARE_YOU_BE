package org.badminton.domain.domain.auth.service;

import org.badminton.domain.domain.auth.info.MemberDeleteInfo;
import org.badminton.domain.domain.member.MemberReader;
import org.badminton.domain.domain.member.MemberStore;
import org.badminton.domain.domain.member.entity.Member;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final MemberReader memberReader;
	private final MemberStore memberStore;
	private final RestTemplate restTemplate;

	@Value("${spring.security.oauth2.revoke-url.naver}")
	private String naverRevokeUrl;
	@Value("${spring.security.oauth2.revoke-url.kakao}")
	private String kakaoRevokeUrl;
	@Value("${spring.security.oauth2.revoke-url.google}")
	private String googleRevokeUrl;
	@Value("${spring.security.oauth2.client.registration.naver.client-id}")
	private String naverClientId;
	@Value("${spring.security.oauth2.client.registration.naver.client-secret}")
	private String naverClientSecret;

	@Override
	public MemberDeleteInfo deleteMember(String memberToken, String oauthToken, String registrationId) {
		MemberDeleteInfo memberDeleteInfo = changeIsDeleted(memberToken);
		unLinkOAuth(registrationId, oauthToken);
		return memberDeleteInfo;
	}

	@Override
	public void logoutMember(String memberToken, HttpServletResponse response) {
		Member member = memberReader.getMember(memberToken);
		member.updateRefreshToken(null);
		memberStore.store(member);
		response.setHeader("Authorization", "");
	}

	private MemberDeleteInfo changeIsDeleted(String memberToken) {
		Member member = memberReader.getMember(memberToken);
		member.doWithdrawal();
		memberStore.store(member);
		log.info("Member marked as deleted: {}", memberToken);
		return MemberDeleteInfo.fromMemberDeleteInfo(member);
	}

	private void unLinkOAuth(String registrationId, String oauthToken) {
		log.info("Unlinking OAuth for provider: {}", registrationId);

		try {
			switch (registrationId.toLowerCase()) {
				case "google":
					unlinkGoogle(oauthToken);
					break;
				case "naver":
					unlinkNaver(oauthToken);
					break;
				case "kakao":
					unlinkKakao(oauthToken);
					break;
				default:
					log.error("Unsupported OAuth provider: {}", registrationId);
					throw new IllegalArgumentException("Unsupported OAuth provider");
			}
			log.info("Successfully unlinked OAuth for provider: {}", registrationId);
		} catch (Exception e) {
			log.error("Error unlinking OAuth for provider: {}", registrationId, e);
		}
	}

	private void unlinkGoogle(String oauthToken) {
		String unlinkUrl = UriComponentsBuilder.fromHttpUrl(googleRevokeUrl)
			.queryParam("token", oauthToken)
			.build()
			.toUriString();

		ResponseEntity<String> response = restTemplate.getForEntity(unlinkUrl, String.class);
		if (response.getStatusCode().is2xxSuccessful()) {
			log.info("Successfully unlinked Google account");
		} else {
			log.error("Failed to unlink Google account. Status: {}, Body: {}",
				response.getStatusCode(), response.getBody());
		}
	}

	private void unlinkNaver(String oauthToken) {
		String unlinkUrl = UriComponentsBuilder.fromHttpUrl(naverRevokeUrl)
			.queryParam("grant_type", "delete")
			.queryParam("client_id", naverClientId)
			.queryParam("client_secret", naverClientSecret)
			.queryParam("access_token", oauthToken)
			.queryParam("service_provider", "NAVER")
			.build()
			.toUriString();

		log.info("Sending request to unlink Naver account: {}", unlinkUrl);

		ResponseEntity<String> response = restTemplate.getForEntity(unlinkUrl, String.class);
		if (response.getStatusCode().is2xxSuccessful()) {
			log.info("Successfully unlinked Naver account");
		} else {
			log.error("Failed to unlink Naver account. Status: {}, Body: {}",
				response.getStatusCode(), response.getBody());
		}

	}

	private void unlinkKakao(String oauthToken) {
		String unlinkUrl = UriComponentsBuilder.fromHttpUrl(kakaoRevokeUrl)
			.build()
			.toUriString();
		HttpHeaders headers = new HttpHeaders();
		headers.setBearerAuth(oauthToken);
		HttpEntity<String> entity = new HttpEntity<>("", headers);

		ResponseEntity<String> response = restTemplate.exchange(unlinkUrl, HttpMethod.POST, entity, String.class);
		if (response.getStatusCode().is2xxSuccessful()) {
			log.info("Successfully unlinked Kakao account");
		} else {
			log.error("Failed to unlink Kakao account. Status: {}, Body: {}",
				response.getStatusCode(), response.getBody());
		}
	}
}

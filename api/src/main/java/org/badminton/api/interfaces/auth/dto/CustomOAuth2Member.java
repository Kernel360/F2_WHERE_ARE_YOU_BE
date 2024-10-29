package org.badminton.api.interfaces.auth.dto;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.badminton.api.interfaces.member.dto.MemberResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomOAuth2Member implements OAuth2User {

	private final MemberResponse memberResponse;

	@Getter
	private final String registrationId;

	private final String oAuthAccessToken;
	private Map<String, String> clubRoles = new HashMap<>();

	public String getOAuthAccessToken() {
		return this.oAuthAccessToken;
	}

	public void addClubRole(String clubToken, String role) {
		this.clubRoles.clear();
		this.clubRoles.put(clubToken, role);
	}

	public String getClubRole(String clubToken) {
		return this.clubRoles.get(clubToken);
	}

	@Override
	public Map<String, Object> getAttributes() {
		return null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + memberResponse.authorization()));
		for (Map.Entry<String, String> entry : clubRoles.entrySet()) {
			String role = entry.getValue().startsWith("ROLE_") ? entry.getValue() : "ROLE_" + entry.getValue();
			authorities.add(new SimpleGrantedAuthority(entry.getKey() + ":" + role));
		}
		return authorities;
	}

	@Override
	public String getName() {
		return memberResponse.name();
	}

	public String getProviderId() {
		return memberResponse.providerId();
	}

	public String getAuthorization() {
		return memberResponse.authorization();
	}

	public String getMemberToken() {
		return memberResponse.memberToken();
	}

	public String getEmail() {
		return memberResponse.email();
	}

	public String getProfileImage() {
		return memberResponse.profileImage();
	}

}

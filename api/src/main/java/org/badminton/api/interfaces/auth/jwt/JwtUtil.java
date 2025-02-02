package org.badminton.api.interfaces.auth.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.badminton.domain.domain.member.MemberReader;
import org.badminton.domain.domain.member.entity.Member;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtil {
	private static final long ACCESS_TOKEN_EXPIRY = 60 * 60 * 1000L;
	private static final long REFRESH_TOKEN_EXPIRY = 7 * 24 * 60 * 60 * 1000L;

	private final SecretKey secretKey;
	private final MemberReader memberReader;
	@Value("${custom.server.domain}")
	private String domain;

	public JwtUtil(@Value("${spring.jwt.secret}") String secret, MemberReader memberReader) {
		secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
			Jwts.SIG.HS256.key().build().getAlgorithm());
		this.memberReader = memberReader;
	}

	public String createAccessToken(String memberToken, List<String> roles) {

		return Jwts.builder()
			.claim("memberToken", memberToken)
			.claim("roles", String.join(",", roles))
			.issuedAt(new Date(System.currentTimeMillis()))
			.expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRY))
			.signWith(secretKey)
			.compact();
	}

	public String createOauthToken(String registrationId, String oAuthAccessToken) {
		return Jwts.builder()
			.claim("registrationId", registrationId)
			.claim("oAuthAccessToken", oAuthAccessToken)
			.issuedAt(new Date(System.currentTimeMillis()))
			.expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRY))
			.signWith(secretKey)
			.compact();
	}

	public String createRefreshToken(String memberToken) {
		return Jwts.builder()
			.claim("memberToken", memberToken)
			.issuedAt(new Date(System.currentTimeMillis()))
			.expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRY))
			.signWith(secretKey)
			.compact();
	}

	public void setAccessTokenCookie(HttpServletResponse response, String accessToken) {
		ResponseCookie cookie = ResponseCookie.from("access_token", accessToken)
			.httpOnly(true)
			.secure(true)
			.sameSite("None")
			.domain(domain)
			.path("/")
			.maxAge(ACCESS_TOKEN_EXPIRY) // 14일
			.build();
		response.addHeader("Set-Cookie", cookie.toString());
	}

	public void setOauthTokenCookie(HttpServletResponse response, String oauthToken) {
		ResponseCookie cookie = ResponseCookie.from("oauth_token", oauthToken)
			.httpOnly(true)
			.secure(true)
			.sameSite("None")
			.domain(domain)
			.path("/")
			.maxAge(REFRESH_TOKEN_EXPIRY / 1000) // 14일
			.build();
		response.addHeader("Set-Cookie", cookie.toString());
	}

	public String getMemberToken(String token) {
		return getDetail(token, "memberToken");
	}

	public String getRegistrationId(String token) {
		return getDetail(token, "registrationId");
	}

	public String getOAuthToken(String token) {
		return getDetail(token, "oAuthAccessToken");
	}

	public List<String> getRoles(String token) {
		String rolesString = getDetail(token, "roles");
		return (rolesString != null) ? List.of(rolesString.split(",")) : Collections.emptyList(); // 문자열을 List로 변환
	}

	public String getDetail(String token, String details) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload()
			.get(details, String.class);
	}

	public String extractAccessTokenFromCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		return cookieValue(cookies, "access_token");
	}

	public String extractOauthTokenFromCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		return cookieValue(cookies, "oauth_token");
	}

	public String cookieValue(Cookie[] cookies, String key) {
		if (cookies == null) {
			return null;
		}
		return Arrays.stream(cookies)
			.filter(cookie -> key.equals(cookie.getName()))
			.map(Cookie::getValue)
			.findFirst()
			.orElse(null);
	}

	public void removeAccessTokenCookie(HttpServletResponse response) {
		ResponseCookie cookie = ResponseCookie.from("access_token", "")
			.httpOnly(true)
			.secure(true)
			.sameSite("None")
			.domain(domain)
			.path("/")
			.maxAge(0) // 쿠키 삭제
			.build();
		response.addHeader("Set-Cookie", cookie.toString());
	}

	public void removeOauthTokenCookie(HttpServletResponse response) {
		ResponseCookie cookie = ResponseCookie.from("oauth_token", "")
			.httpOnly(true)
			.secure(true)
			.sameSite("None")
			.domain(domain)
			.path("/")
			.maxAge(0) // 쿠키 삭제
			.build();
		response.addHeader("Set-Cookie", cookie.toString());
	}

	public boolean refreshAccessToken(String accessToken, HttpServletResponse response) {
		if (accessToken == null || accessToken.isEmpty()) {
			removeAccessTokenCookie(response);
			removeOauthTokenCookie(response);
			return false;
		}

		String memberToken = getMemberTokenIfAccessTokenExpired(accessToken);
		if (Objects.isNull(memberToken)) {
			return true;
		}

		Member member = memberReader.getMember(memberToken);
		String refreshToken = member.getRefreshToken();

		if (refreshToken == null || refreshToken.isEmpty()) {
			log.error("Refresh token is null or empty");
			removeAccessTokenCookie(response);
			removeOauthTokenCookie(response);
			return false; // 실패 처리
		}

		try {
			String validate = getMemberTokenIfAccessTokenExpired(refreshToken);
			if (Objects.isNull(validate)) {
				List<String> roles = getRoles(refreshToken);
				String newAccessToken = createAccessToken(memberToken, roles);
				setAccessTokenCookie(response, newAccessToken);
				return true;
			}
		} catch (Exception e) {
			log.error("Failed to validate refresh token", e);
		}

		removeAccessTokenCookie(response);
		removeOauthTokenCookie(response);
		return false;
	}

	public String getMemberTokenIfAccessTokenExpired(String token) {
		if (token == null || token.isEmpty()) {
			log.error("Access token is null or empty");
			return null;
		}
		try {
			var claims = Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();

			return null;

		} catch (ExpiredJwtException e) {
			return e.getClaims().get("memberToken", String.class); // 만료된 경우 memberToken 반환
		}
	}

	public boolean isAliveOauthToken(String token) {
		try {
			var claims = Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();

			return true;

		} catch (Exception e) {
			return false;
		}
	}
}

package org.badminton.api.config.security;

import java.util.Arrays;
import java.util.Optional;

import org.badminton.api.application.auth.CustomOAuth2MemberService;
import org.badminton.api.filter.ClubMembershipFilter;
import org.badminton.api.filter.JwtAuthenticationFilter;
import org.badminton.api.interfaces.auth.jwt.JwtUtil;
import org.badminton.api.interfaces.auth.successhandler.CustomSuccessHandler;
import org.badminton.domain.domain.clubmember.ClubMemberReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

	private final CustomOAuth2MemberService customOAuth2MemberService;
	private final CustomSuccessHandler customSuccessHandler;
	private final JwtUtil jwtUtil;
	private final ClubPermissionEvaluator clubPermissionEvaluator;
	private final ClubMemberReader clubMemberReader;
	@Value("${custom.server.front}")
	private String frontUrl;

	@Value("${custom.server.https}")
	private String serverUrl;

	@Value("${custom.server.local}")
	private String serverLocal;

	// OAuth2 로그인 및 공개 경로: 인증 없이 접근 가능하며, OAuth2 로그인 설정 포함
	@Bean
	public SecurityFilterChain publicFilterChain(HttpSecurity http) throws Exception {
		http
			.securityMatcher("/", "/oauth2/**", "/login/**", "/error",
				"/swagger-ui/**")
			.csrf(AbstractHttpConfigurer::disable)
			.cors(this::corsConfigurer)
			.authorizeHttpRequests(auth -> auth
				.anyRequest().permitAll())
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.oauth2Login(oauth2 -> oauth2
				.userInfoEndpoint(
					userInfoEndpointConfig -> userInfoEndpointConfig.userService(customOAuth2MemberService))
				.successHandler(customSuccessHandler));
		return http.build();
	}

	// JWT 인증만 필요한 경로
	@Bean
	@Order(1)
	public SecurityFilterChain jwtOnlyFilterChain(HttpSecurity http) throws Exception {
		http
			.securityMatcher(
				request ->
					request.getMethod().equals("POST")
						&& request.getRequestURI().equals("/v1/clubs") || request.getRequestURI()
						.startsWith("/v1/members")
						|| request.getRequestURI().equals("/v1/members/profileImage")
						|| request.getRequestURI()
						.equals("/v1/clubs/images")
						|| request.getRequestURI().equals("/v1/members/is-club-member")
						|| request.getRequestURI().equals("/v1/members/matchesRecord")
			)
			.csrf(AbstractHttpConfigurer::disable)
			.cors(this::corsConfigurer)
			.addFilterBefore(new JwtAuthenticationFilter(jwtUtil, clubMemberReader),
				UsernamePasswordAuthenticationFilter.class)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth
				.anyRequest().authenticated());
		return http.build();
	}

	@Bean
	@Order(2)
	public SecurityFilterChain clubFilterChain(HttpSecurity http) throws Exception {
		http
			.securityMatcher("/v1/clubs/**")
			.csrf(AbstractHttpConfigurer::disable)
			.cors(this::corsConfigurer)
			.addFilterBefore(new JwtAuthenticationFilter(jwtUtil, clubMemberReader),
				UsernamePasswordAuthenticationFilter.class)
			.addFilterAfter(new ClubMembershipFilter(clubMemberReader), JwtAuthenticationFilter.class)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth
				.requestMatchers(HttpMethod.GET, "/v1/clubs", "/v1/clubs/{clubToken}", "/v1/clubs/search")
				.permitAll()
				.requestMatchers(HttpMethod.POST, "/v1/clubs")
				.permitAll()
				.requestMatchers(HttpMethod.DELETE, "/v1/clubs/{clubToken}")
				.access(hasClubRole("OWNER"))
				.requestMatchers(HttpMethod.PATCH, "/v1/clubs/{clubToken}")
				.access(hasClubRole("OWNER", "MANAGER"))
				.requestMatchers(HttpMethod.GET, "/v1/clubs/{clubToken}/leagues/{leagueId}")
				.access(hasClubRole("OWNER", "MANAGER", "USER"))
				.requestMatchers(HttpMethod.GET, "/v1/clubs/{clubToken}/clubMembers")
				.access(hasClubRole("OWNER", "MANAGER", "USER"))
				.requestMatchers(HttpMethod.GET, "/v1/clubs/{clubToken}")
				.authenticated()
				.requestMatchers(HttpMethod.POST, "/v1/clubs/{clubToken}/leagues")
				.access(hasClubRole("OWNER", "MANAGER"))
				.requestMatchers(HttpMethod.POST, "/v1/clubs/{clubToken}/league", "/v1/clubs/images")
				.access(hasClubRole("OWNER", "MANAGER"))
				.requestMatchers(HttpMethod.DELETE, "/v1/clubs/{clubToken}/leagues/{leagueId}")
				.access(hasClubRole("OWNER", "MANAGER"))
				.requestMatchers(HttpMethod.PATCH, "/v1/clubs/{clubToken}/leagues/{leagueId}")
				.access(hasClubRole("OWNER", "MANAGER"))
				.requestMatchers(HttpMethod.POST, "/v1/clubs/{clubToken}/leagues/{leagueId}/participation")
				.access(hasClubRole("OWNER", "MANAGER", "USER"))
				.requestMatchers(HttpMethod.DELETE, "/v1/clubs/{clubToken}/leagues/{leagueId}/participation")
				.access(hasClubRole("OWNER", "MANAGER", "USER"))
				.requestMatchers(HttpMethod.PATCH, "/v1/clubs/{clubToken}/clubMembers/role",
					"v1/clubs/{clubToken}/clubMembers/ban", "v1/clubs/{clubToken}/clubMembers/expel")
				.access(hasClubRole("OWNER"))
				.anyRequest()
				.authenticated()
			);
		return http.build();
	}

	private AuthorizationManager<RequestAuthorizationContext> hasClubRole(String... roles) {
		return (authentication, context) -> {
			Authentication auth = authentication.get();
			if (auth == null || !auth.isAuthenticated()) {
				return new AuthorizationDecision(false);
			}

			String clubToken = getClubTokenFromContext(context);
			if (clubToken == null) {
				return new AuthorizationDecision(false);
			}

			boolean hasRole = clubPermissionEvaluator.hasClubRole(auth, clubToken, roles);

			log.info("""
				Checking roles for clubToken: {}
				User authorities: {}
				Required roles: {}
				Has required role: {}
				""", clubToken, auth.getAuthorities(), Arrays.toString(roles), hasRole
			);

			return new AuthorizationDecision(hasRole);
		};
	}

	private String getClubTokenFromContext(RequestAuthorizationContext context) {

		String clubToken = context.getVariables().get("clubToken");
		if (clubToken != null) {
			return clubToken;
		}
		HttpServletRequest request = context.getRequest();
		return Optional.ofNullable(request.getParameter("clubToken"))
			.filter(s -> !s.isEmpty())
			.orElse(null);
	}

	private void corsConfigurer(CorsConfigurer<HttpSecurity> corsConfigurer) {
		corsConfigurer.configurationSource(request -> {
			CorsConfiguration configuration = new CorsConfiguration();
			configuration.setAllowedOrigins(Arrays.asList(frontUrl, serverUrl, serverLocal));
			configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
			configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
			configuration.setAllowCredentials(true);
			configuration.setMaxAge(3600L);
			return configuration;
		});
	}
}

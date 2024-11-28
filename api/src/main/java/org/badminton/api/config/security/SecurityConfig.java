package org.badminton.api.config.security;

import java.util.Arrays;

import org.badminton.api.application.auth.CustomOAuth2MemberService;
import org.badminton.api.exceptionhandler.FailedAuthenticationEntryPoint;
import org.badminton.api.filter.JwtAuthenticationFilter;
import org.badminton.api.interfaces.auth.jwt.JwtUtil;
import org.badminton.api.interfaces.auth.successhandler.CustomSuccessHandler;
import org.badminton.domain.domain.clubmember.ClubMemberReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomOAuth2MemberService customOAuth2MemberService;
	private final CustomSuccessHandler customSuccessHandler;
	private final JwtUtil jwtUtil;
	private final ClubPermissionEvaluator clubPermissionEvaluator;
	private final ClubMemberReader clubMemberReader;
	private final FailedAuthenticationEntryPoint failedAuthenticationEntryPoint;

	@Value("${custom.server.front}")
	private String frontUrl;

	@Value("${custom.server.https}")
	private String serverUrl;

	@Value("${custom.server.local}")
	private String serverLocal;

	@Bean
	public SecurityFilterChain publicFilterChain(HttpSecurity http) throws Exception {
		http
			.securityMatcher("/", "/oauth2/**", "/login/**", "/error", "/swagger-ui/**", "/v1/leagues/**",
				"/v1/members/session",
				"/v1/clubs/{clubToken}/clubMembers/check")
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
						|| request.getRequestURI().equals("/v1/clubs/images")
						|| request.getRequestURI().equals("/v1/members/matchesRecord")
						|| request.getRequestURI().equals("/v1/clubs/{clubToken}/clubMembers")
						|| request.getRequestURI().equals("/v1/clubs/{clubToken}/clubMembers/check")
						|| request.getRequestURI().equals("/v1/clubs/{clubToken}/leagues/month")
						|| request.getRequestURI().equals("/v1/clubs/{clubToken}/leagues/date")
						|| request.getRequestURI().equals("/v1/clubs/{clubToken}/leagues/{leagueId}/check")
			)
			.csrf(AbstractHttpConfigurer::disable)
			.cors(this::corsConfigurer)
			.exceptionHandling(
				exception -> exception.authenticationEntryPoint(failedAuthenticationEntryPoint))
			.addFilterBefore(new JwtAuthenticationFilter(jwtUtil, clubMemberReader),
				UsernamePasswordAuthenticationFilter.class)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("v1/members/send").permitAll()
				.requestMatchers(HttpMethod.POST, "/v1/clubs/{clubToken}/clubMembers/").authenticated()
				.requestMatchers("/v1/clubs/{clubToken}/leagues/month").permitAll()
				.requestMatchers("/v1/clubs/{clubToken}/leagues/date").permitAll()
				.anyRequest().authenticated());
		return http.build();
	}

	// @Bean
	// @Order(2)
	// public SecurityFilterChain clubFilterChain(HttpSecurity http) throws Exception {
	// 	http
	// 		.securityMatcher("/v1/clubs/**")
	// 		.csrf(AbstractHttpConfigurer::disable)
	// 		.cors(this::corsConfigurer)
	// 		.addFilterBefore(new JwtAuthenticationFilter(jwtUtil, clubMemberReader),
	// 			UsernamePasswordAuthenticationFilter.class)
	// 		.addFilterAfter(new ClubMembershipFilter(clubMemberReader), JwtAuthenticationFilter.class)
	// 		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	// 		.exceptionHandling(
	// 			exception -> exception.authenticationEntryPoint(failedAuthenticationEntryPoint))
	// 		.authorizeHttpRequests(auth -> auth
	// 			.anyRequest()
	// 			.authenticated()
	// 		);
	// 	return http.build();
	// }

	// private AuthorizationManager<RequestAuthorizationContext> hasClubRole(String... roles) {
	// 	return (authentication, context) -> {
	//
	// 		Authentication auth = authentication.get();
	// 		if (auth == null || !auth.isAuthenticated()) {
	// 			return new AuthorizationDecision(false);
	// 		}
	//
	// 		String clubToken = getClubTokenFromContext(context);
	// 		if (clubToken == null) {
	// 			return new AuthorizationDecision(false);
	// 		}
	//
	// 		boolean hasRole = clubPermissionEvaluator.hasClubRole(auth, clubToken, roles);
	//
	// 		log.info("""
	// 			Checking roles for clubToken: {}
	// 			User authorities: {}
	// 			Required roles: {}
	// 			Has required role: {}
	// 			""", clubToken, auth.getAuthorities(), Arrays.toString(roles), hasRole
	// 		);
	//
	// 		if (!hasRole) {
	// 			throw new AuthenticationException("Insufficient roles to access this resource") {
	// 			};
	// 		}
	// 		return new AuthorizationDecision(hasRole);
	// 	};
	// }

	// private String getClubTokenFromContext(RequestAuthorizationContext context) {
	//
	// 	String clubToken = context.getVariables().get("clubToken");
	// 	if (clubToken != null) {
	// 		return clubToken;
	// 	}
	// 	HttpServletRequest request = context.getRequest();
	// 	return Optional.ofNullable(request.getParameter("clubToken"))
	// 		.filter(s -> !s.isEmpty())
	// 		.orElse(null);
	// }

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

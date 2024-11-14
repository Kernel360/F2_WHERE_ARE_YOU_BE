package org.badminton.api.interfaces.club;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Locale;

import org.badminton.api.application.club.ClubFacade;
import org.badminton.api.application.club.ClubRankFacade;
import org.badminton.api.interfaces.auth.dto.CustomOAuth2Member;
import org.badminton.api.interfaces.auth.jwt.JwtUtil;
import org.badminton.api.interfaces.club.controller.ClubController;
import org.badminton.api.interfaces.club.controller.ClubDtoMapper;
import org.badminton.api.interfaces.club.dto.ClubCreateRequest;
import org.badminton.api.interfaces.club.dto.ClubUpdateRequest;
import org.badminton.api.interfaces.member.dto.MemberResponse;
import org.badminton.domain.domain.club.command.ClubCreateCommand;
import org.badminton.domain.domain.club.command.ClubUpdateCommand;
import org.badminton.domain.domain.member.info.MemberInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import net.datafaker.Faker;

@SpringBootTest
@AutoConfigureMockMvc
public class ClubControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ClubFacade clubFacade;

	@MockBean
	private ClubRankFacade clubRankFacade;

	@MockBean
	private ClubDtoMapper clubDtoMapper;

	@MockBean
	private JwtUtil jwtUtil;

	@InjectMocks
	private ClubController clubController;

	String clubName;
	String clubDescription;
	String clubImage;

	@BeforeEach
	void setUp() {
		// MemberInfo 객체 생성 (여기서는 예시로 값을 넣음)
		MemberInfo memberInfo = new MemberInfo(
			"me_token_1", "AUTHORIZATION_ADMIN", "John Doe", "john.doe@example.com",
			"1070449979547641023123", "profileImageUrl"
		);

		// MemberResponse 객체 생성
		MemberResponse memberResponse = MemberResponse.fromMemberInfo(memberInfo);

		// CustomOAuth2Member 객체 생성
		CustomOAuth2Member customMember = new CustomOAuth2Member(memberResponse, "google", "oauthAccessToken");

		// 사용자 인증 객체 설정
		UsernamePasswordAuthenticationToken authentication =
			new UsernamePasswordAuthenticationToken(customMember, null, customMember.getAuthorities());

		// SecurityContext에 인증 정보 설정
		SecurityContextHolder.getContext().setAuthentication(authentication);
		Faker faker = new Faker(new Locale("ko", "KO"));
		clubName = faker.company().name();
		clubDescription = faker.company().catchPhrase();
	}

	@Test
	@WithMockUser(username = "me_token_1", authorities = {"AUTHORIZATION_ADMIN"})
	void createClubTest() throws Exception {
		ClubCreateRequest clubCreateRequest = new ClubCreateRequest(
			clubName, clubDescription,
			"https://d36om9pjoifd2y.cloudfront.net/club-banner/3257bbf2-52dc-41b2-9d1f-a6c66f3c7216.avif");

		ClubCreateCommand clubCreateCommand = new ClubCreateCommand(clubName, clubDescription,
			"https://d36om9pjoifd2y.cloudfront.net/club-banner/3257bbf2-52dc-41b2-9d1f-a6c66f3c7216.avif");

		when(clubDtoMapper.of(clubCreateRequest)).thenReturn(clubCreateCommand);
		clubFacade.createClub(clubCreateCommand, "me_token_1");

		mockMvc.perform(post("/v1/clubs").with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"clubName\":\"" + clubCreateRequest.clubName() + "\","
					+ "\"clubDescription\":\"" + clubCreateRequest.clubDescription() + "\","
					+ "\"clubImage\":\"" + clubCreateRequest.clubImage() + "\"}"))
			.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "provider_1", authorities = {"AUTHORIZATION_ADMIN"})
	void updateClubTest() throws Exception {
		String clubToken = "club_asdq7UjhInO0BhBO";
		ClubUpdateRequest clubUpdateRequest = new ClubUpdateRequest(
			clubName, clubDescription,
			"https://d36om9pjoifd2y.cloudfront.net/club-banner/3257bbf2-52dc-41b2-9d1f-a6c66f3c7216.avif");
		ClubUpdateCommand clubUpdateCommand = new ClubUpdateCommand(clubName, clubDescription,
			"https://d36om9pjoifd2y.cloudfront.net/club-banner/3257bbf2-52dc-41b2-9d1f-a6c66f3c7216.avif");

		when(clubDtoMapper.of(clubUpdateRequest)).thenReturn(clubUpdateCommand);

		clubFacade.updateClubInfo(clubUpdateCommand, "club_asdq7UjhInO0BhBO");

		mockMvc.perform(patch("/v1/clubs/{clubToken}", clubToken).with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"clubName\":\"" + clubUpdateRequest.clubName() + "\","
					+ "\"clubDescription\":\"" + clubUpdateRequest.clubDescription() + "\","
					+ "\"clubImage\":\"" + clubUpdateRequest.clubImage() + "\"}"))
			.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "testUser", roles = {"USER"})
	void readClubTest() throws Exception {
		mockMvc.perform(get("/v1/clubs/{clubToken}", "existingClubToken"))
			.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "testUser", roles = {"USER"})
	void deleteClubTest() throws Exception {
		mockMvc.perform(delete("/v1/clubs/{clubToken}", "existingClubToken").with(csrf()))
			.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "testUser", roles = {"USER"})
	void readAllClubTest() throws Exception {
		mockMvc.perform(get("/v1/clubs")
				.with(csrf())
				.param("page", "0")
				.param("size", "10")
				.param("sort", "clubName"))
			.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "testUser", roles = {"USER"})
	void clubSearchTest() throws Exception {
		mockMvc.perform(get("/v1/clubs/search")
				.param("keyword", "club")
				.param("page", "0")
				.param("size", "10")
				.param("sort", "clubName"))
			.andExpect(status().isOk());
	}

	@Test
	@WithMockUser(username = "testUser", roles = {"USER"})
	void clubSearchPopularTest() throws Exception {
		mockMvc.perform(get("/v1/clubs/popular"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isArray());
	}

	@Test
	@WithMockUser(username = "testUser", roles = {"USER"})
	void clubSearchActivityTest() throws Exception {
		mockMvc.perform(get("/v1/clubs/activity"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isArray());
	}

	@Test
	@WithMockUser(username = "testUser", roles = {"USER"})
	void clubSearchRecentlyTest() throws Exception {
		mockMvc.perform(get("/v1/clubs/recently"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").isArray());
	}

	@Test
	@WithMockUser(username = "testUser", roles = {"USER"})
	void getClubApplicantTest() throws Exception {
		mockMvc.perform(get("/v1/clubs/{clubToken}/applicants", "existingClubToken"))
			.andExpect(status().isOk());
	}
}

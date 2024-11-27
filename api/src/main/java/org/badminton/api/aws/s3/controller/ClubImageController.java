package org.badminton.api.aws.s3.controller;

import org.badminton.api.aws.s3.model.dto.ImageUploadRequest;
import org.badminton.api.aws.s3.service.ClubImageService;
import org.badminton.api.common.response.CommonResponse;
import org.badminton.api.interfaces.auth.dto.CustomOAuth2Member;
import org.badminton.domain.common.policy.ClubMemberPolicy;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/clubs/{clubToken}/images")
public class ClubImageController {
	private final ClubImageService clubImageService;
	private final ClubMemberPolicy clubMemberPolicy;

	@PostMapping
	@Operation(
		summary = "클럽 이미지 업로드",
		description = "이미지를 S3에 업로드하는 API 입니다.",
		requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
			required = true,
			content = @Content(
				mediaType = "multipart/form-data",
				schema = @Schema(implementation = ImageUploadRequest.class)
			)
		)
	)
	public CommonResponse<String> saveImage(@RequestPart("multipartFile") MultipartFile multipartFile,
		@AuthenticationPrincipal CustomOAuth2Member member,
		@PathVariable String clubToken) {
		clubMemberPolicy.validateClubOwner(member.getMemberToken(), clubToken);
		ImageUploadRequest request = new ImageUploadRequest(multipartFile);
		return CommonResponse.success(clubImageService.uploadFile(request));
	}
}

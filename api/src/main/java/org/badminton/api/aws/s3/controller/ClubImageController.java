package org.badminton.api.aws.s3.controller;

import org.badminton.api.application.clubImage.ClubImageFacade;
import org.badminton.api.aws.s3.model.dto.ImageUploadRequest;
import org.badminton.api.common.response.CommonResponse;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/clubs/images")
public class ClubImageController {
	private final ClubImageFacade clubImageFacade;

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
	public CommonResponse<String> saveImage(@ModelAttribute ImageUploadRequest request) {
		return CommonResponse.success(clubImageFacade.saveImage(request));
	}
}

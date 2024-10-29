package org.badminton.api.aws.s3.model.dto;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record ImageUploadRequest(
	@Schema(description = "업로드할 이미지 파일", type = "string", format = "binary")
	MultipartFile multipartFile
) {
}


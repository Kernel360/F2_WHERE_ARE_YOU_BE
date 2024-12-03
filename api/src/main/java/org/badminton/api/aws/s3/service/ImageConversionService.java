package org.badminton.api.aws.s3.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.badminton.api.common.exception.EmptyFileException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sksamuel.scrimage.ImmutableImage;
import com.sksamuel.scrimage.metadata.ImageMetadata;
import com.sksamuel.scrimage.webp.WebpWriter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageConversionService {

	public byte[] convertToWebP(MultipartFile file) {
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			 InputStream inputStream = file.getInputStream()) {

			// 스트림을 한 번만 열어 사용
			ImmutableImage image = ImmutableImage.loader().fromStream(inputStream);
			WebpWriter writer = WebpWriter.DEFAULT;

			// 메타데이터는 비워두거나 생성
			ImageMetadata metadata = ImageMetadata.empty;

			writer.write(image, metadata, outputStream);

			return outputStream.toByteArray();
		} catch (IOException exception) {
			log.error("Error converting image to WebP: {}", exception.getMessage(), exception);
			throw new EmptyFileException(exception);
		}
	}
}

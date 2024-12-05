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

			ImmutableImage image = ImmutableImage.loader().fromStream(inputStream);
			WebpWriter writer = WebpWriter.DEFAULT;

			ImageMetadata metadata = ImageMetadata.empty;

			writer.write(image, metadata, outputStream);

			return outputStream.toByteArray();
		} catch (IOException exception) {
			throw new EmptyFileException(exception);
		}
	}
}

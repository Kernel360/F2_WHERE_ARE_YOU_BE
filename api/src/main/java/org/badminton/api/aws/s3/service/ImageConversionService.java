package org.badminton.api.aws.s3.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.badminton.api.common.exception.EmptyFileException;
import org.springframework.stereotype.Service;

import com.sksamuel.scrimage.ImmutableImage;
import com.sksamuel.scrimage.metadata.ImageMetadata;
import com.sksamuel.scrimage.webp.WebpWriter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageConversionService {

	public byte[] convertToWebP(byte[] fileName) {
		try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

			log.info("try 시작 부분 :{}", fileName);

			InputStream inputStream = new ByteArrayInputStream(fileName);

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

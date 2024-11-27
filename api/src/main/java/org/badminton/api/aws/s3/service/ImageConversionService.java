package org.badminton.api.aws.s3.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.badminton.api.common.exception.EmptyFileException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sksamuel.scrimage.ImmutableImage;
import com.sksamuel.scrimage.metadata.ImageMetadata;
import com.sksamuel.scrimage.webp.WebpWriter;

@Service
public class ImageConversionService {

	public byte[] convertToWebP(MultipartFile file) {
		try {
			ImmutableImage image = ImmutableImage.loader().fromStream(file.getInputStream());
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			WebpWriter writer = WebpWriter.DEFAULT;
			writer.write(image, ImageMetadata.fromStream(file.getInputStream()), outputStream);
			return outputStream.toByteArray();
		} catch (IOException exception) {
			throw new EmptyFileException(exception);
		}

	}
}

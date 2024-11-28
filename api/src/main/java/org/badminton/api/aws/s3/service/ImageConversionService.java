package org.badminton.api.aws.s3.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.badminton.api.common.exception.EmptyFileException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ImageConversionService {

	public byte[] convertToWebP(MultipartFile file) {
		try {
			BufferedImage inputImage = ImageIO.read(file.getInputStream());
			if (inputImage == null) {
				throw new EmptyFileException();
			}

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ImageIO.write(inputImage, "webp", outputStream);

			return outputStream.toByteArray();
		} catch (IOException exception) {
			throw new EmptyFileException(exception);
		}

		// try {
		// 	ImmutableImage image = ImmutableImage.loader().fromStream(file.getInputStream());
		// 	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		// 	WebpWriter writer = WebpWriter.DEFAULT;
		// 	writer.write(image, ImageMetadata.fromStream(file.getInputStream()), outputStream);
		// 	return outputStream.toByteArray();
		// } catch (IOException exception) {
		// 	throw new EmptyFileException(exception);
		// }
	}
}

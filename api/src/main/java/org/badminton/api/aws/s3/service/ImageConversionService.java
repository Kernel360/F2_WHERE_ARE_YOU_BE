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
			// Read input image from MultipartFile
			BufferedImage inputImage = ImageIO.read(file.getInputStream());
			if (inputImage == null) {
				throw new EmptyFileException();
			}

			// Write output image as WebP
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ImageIO.write(inputImage, "webp", outputStream);

			return outputStream.toByteArray();
		} catch (IOException exception) {
			log.error("Image conversion failed for file: {}", file.getOriginalFilename(), exception);
			throw new EmptyFileException(exception);
		}
	}
}

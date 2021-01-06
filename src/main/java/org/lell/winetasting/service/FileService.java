package org.lell.winetasting.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Optional;

@Component
public class FileService {

	private static final Logger logger = LoggerFactory.getLogger(FileService.class);

	@Value("#{${pictures.path}}")
	private String imagePath;

	public File createNewFileByMultipartFile(final MultipartFile multipartFile, final String filename) throws IOException {
		try (final FileOutputStream fileOutputStream = new FileOutputStream(filename)) {
			fileOutputStream.write(multipartFile.getBytes());
			final File file = new File(filename);
			logger.info("File path for new file {} ", file.getAbsolutePath());
			return file;
		}
	}

	public Optional<File> getFileByExistingFileName(final String filePath) {
		if (filePath != null) {
			final File file = Paths.get(imagePath + "/" + filePath).toFile();
			if (file.exists()) {
				return Optional.of(file);
			}
		}
		return Optional.empty();
	}
}

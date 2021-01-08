package org.lell.winetasting.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public final class FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

    public File createNewFileByMultipartFile(final MultipartFile multipartFile, final Path targetPath) throws IOException {
        final File targetFile = targetPath.toFile();
        try (final FileOutputStream fileOutputStream = new FileOutputStream(targetFile)) {
            fileOutputStream.write(multipartFile.getBytes());
            logger.info("File path for new file {} ", targetFile.getAbsolutePath());
            return targetFile;
        }
    }

    public Optional<File> getFileByPath(final Path path) {
        if (path != null) {
            final File file = path.toFile();
            if (file.exists()) {
                return Optional.of(file);
            }
        }
        return Optional.empty();
    }

    public void deleteIfExist(final Path path) throws IOException {
        final boolean exists = Files.exists(path);
        if (exists) {
            Files.delete(path);
        }
    }
}

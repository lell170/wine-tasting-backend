package org.lell.winetasting.service;

import org.apache.logging.log4j.util.Strings;
import org.lell.winetasting.model.Wine;
import org.lell.winetasting.repository.WineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Component
public class PictureService {

    private static final Logger logger = LoggerFactory.getLogger(PictureService.class);
    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmssSS");

    private final FileService fileService;
    private final WineRepository wineRepository;
    private final WineService wineService;

    @Value("#{${pictures.path}}")
    private String picturesPath;

    public PictureService(final FileService fileService, final WineRepository wineRepository, final WineService wineService) {
        this.fileService = fileService;
        this.wineRepository = wineRepository;
        this.wineService = wineService;
    }

    public void saveNewPicture(final MultipartFile multipartFile, final Wine wine) throws IOException {
        final String newFileName = getNewFileName(wine.getId());
        final Path target = Paths.get(picturesPath + "/" + newFileName);
        final File pictureFile = fileService.createNewFileByMultipartFile(multipartFile, target);
        logger.info("try to save pictureFile {} into path {}", pictureFile.getAbsolutePath(), target);
        Files.move(pictureFile.toPath(), target);

        wine.setPictureFileName(newFileName);
    }

    public String updatePicture(final MultipartFile multipartFile, final long id) throws IOException {
        final Optional<Wine> byId = wineRepository.findById((long) id);

        if (byId.isPresent()) {
            final Wine wine = byId.get();
            System.out.println("image is changed " + this.isImageChanged(multipartFile, wine.getPictureFileName()));
            final boolean imageChanged = this.isImageChanged(multipartFile, wine.getPictureFileName());
            if (imageChanged) {
                this.saveNewPicture(multipartFile, wine);
                wineService.saveWine(wine);
            }
            return wine.getPictureFileName();
        }
        return Strings.EMPTY;
    }

    // here I used something from java 12 :) (Files.mismatch)
    private boolean isImageChanged(final MultipartFile multipartFile, final String existingFileName) throws IOException {
        final Path tmpFile = Paths.get(picturesPath + File.separator + "tmpFile");
        this.fileService.deleteIfExist(tmpFile);

        final Path existingPictureFile = Paths.get(picturesPath + File.separator + existingFileName);
        final File tmpFileFromMultipart = fileService.createNewFileByMultipartFile(multipartFile, tmpFile);
        final Optional<File> existingPicture = fileService.getFileByPath(existingPictureFile);

        if (existingPicture.isPresent()) {
            return !(Files.mismatch(tmpFileFromMultipart.toPath(), existingPicture.get().toPath()) == -1L);
        }

        return true;
    }

    private String getNewFileName(final long wineId) {
        return LocalDateTime.now().format(DATE_TIME_FORMATTER) + "_" + wineId + ".jpg";
    }
}

package org.lell.winetasting.service;

import com.google.common.collect.Streams;
import com.google.gson.Gson;
import org.apache.logging.log4j.util.Strings;
import org.lell.winetasting.model.CountryCode;
import org.lell.winetasting.model.Type;
import org.lell.winetasting.model.Wine;
import org.lell.winetasting.model.WineDTO;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class WineService {

	private static final Logger logger = LoggerFactory.getLogger(WineService.class);
	private final WineRepository wineRepository;
	private final FileService fileService;
	private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmssSS");

	@Value("#{${pictures.path}}")
	private String imagePath;

	public WineService(final WineRepository wineRepository, final FileService fileService) {
		this.wineRepository = wineRepository;
		this.fileService = fileService;
	}

	public List<Wine> getAllWines() {
		return Streams.stream(wineRepository.findByOrderByIdAsc()).collect(Collectors.toList());
	}

	public void addImageToWine(final File picture, final Wine wine) throws IOException {
		final String fileName = LocalDateTime.now().format(DATE_TIME_FORMATTER) + "_" + wine.getId() + ".jpg";
		final Path target = Paths.get(imagePath + "/" + fileName);
		logger.info("try to save file {} into path {}", picture.getAbsolutePath(), target);
		Files.move(picture.toPath(), target);

		wine.setPictureFileName(fileName);
	}

	public Wine parseDTO(final WineDTO wineDTO) {
		final Wine wine = new Wine();
		if (wineDTO.getCreationDate() != null) {
			wine.setCreationDate(LocalDateTime.parse(wineDTO.getCreationDate()));
		} else {
			wine.setCreationDate(LocalDateTime.now());
		}
		wine.setChangeDate(LocalDateTime.now());
		wine.setWineMaker(wineDTO.getWineMaker());
		wine.setPictureFileName(wineDTO.getPictureFileName());
		wine.setDescription(wineDTO.getDescription());
		wine.setCountryCode(CountryCode.valueOf(wineDTO.getCountryCode()));
		wine.setGrape(wineDTO.getGrape());
		wine.setId(wineDTO.getId());
		wine.setName(wineDTO.getName());
		wine.setYear(wineDTO.getYear());
		wine.setType(Type.valueOf(wineDTO.getType()));

		return wine;
	}

	public void saveNewImage(final MultipartFile multipartFile, final Wine wine) throws IOException {
		final File file = fileService.createNewFileByMultipartFile(multipartFile, multipartFile.getOriginalFilename());
		this.addImageToWine(file, wine);
	}

	public void updateWine(final WineDTO wineDTO) {
		final Wine wine = this.parseDTO(wineDTO);
		this.saveWine(wine);
	}

	public String updatePicture(final MultipartFile multipartFile, final int id) throws IOException {
		final Optional<Wine> byId = wineRepository.findById((long) id);
		if (byId.isPresent()) {
			final Wine wine = byId.get();
			final boolean imageChanged = this.isImageChanged(wine.getPictureFileName(), multipartFile.getName());
			if (imageChanged) {
				this.saveNewImage(multipartFile, wine);
				this.saveWine(wine);
			}
			return wine.getPictureFileName();
		}
		return Strings.EMPTY;
	}

	public long createWine(final WineDTO wineDTO) {
		final Wine wine = this.parseDTO(wineDTO);
		return this.saveWine(wine);
	}

	private long saveWine(final Wine wine) {
		if (wine.getCreationDate() == null) {
			wine.setCreationDate(LocalDateTime.now());
		}
		return wineRepository.save(wine).getId();
	}

	// here I used something from java 12 :) (Files.mismatch)
	private boolean isImageChanged(final String formFileName, final String existingFileName) throws IOException {
		final Optional<File> fileObjForFormName = fileService.getFileByExistingFileName(formFileName);
		final Optional<File> fileObjForExistingName = fileService.getFileByExistingFileName(existingFileName);

		if (fileObjForExistingName.isPresent() && fileObjForFormName.isPresent()) {
			return Files.mismatch(fileObjForFormName.get().toPath(), fileObjForExistingName.get().toPath()) == -1L;
		}

		return true;
	}
}

package org.lell.winetasting.controller;

import org.lell.winetasting.model.Wine;
import org.lell.winetasting.model.WineDTO;
import org.lell.winetasting.service.WineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/wine", produces = "application/json")
public class WineController {

	private static final Logger logger = LoggerFactory.getLogger(WineController.class);

	private final WineService wineService;

	WineController(final WineService wineService) {
		this.wineService = wineService;
	}

	@GetMapping(path = "/all")
	public ResponseEntity<List<Wine>> getAllWines() {
		logger.info("received http request get all wines");
		return new ResponseEntity<>(wineService.getAllWines(), HttpStatus.OK);
	}

	@PostMapping(path = "/create")
	public long createWine(@RequestBody final WineDTO wineDTO) {
		logger.info("received http request CREATE new wine {} ", wineDTO);
		return wineService.createWine(wineDTO);
	}

	@PostMapping(path = "/update/{id}/jsonData")
	public void updateWineData(@RequestBody final WineDTO wineDTO, @PathVariable("id") final int id) {
		logger.info("received http request UPDATE existing wine with id {}", id);
		wineDTO.setId(id);
		wineService.updateWine(wineDTO);
	}

	@PostMapping(path = "/update/{id}/picture")
	public ResponseEntity<String> updateWinePicture(@RequestBody final MultipartFile picture, @PathVariable("id") final int id)
			throws IOException {
		logger.info("received http request UPDATE picture by existing wine with id {}", id);
		return new ResponseEntity<>(wineService.updatePicture(picture, id), HttpStatus.OK);
	}
}

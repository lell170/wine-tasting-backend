package org.lell.winetasting.controller;

import org.lell.winetasting.model.Wine;
import org.lell.winetasting.service.WineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
		return new ResponseEntity<>(wineService.getAllWines(), HttpStatus.OK);
	}

	@PostMapping(path = "/updateOrCreate")
	@ResponseBody
	public String updateOrCreate(@RequestParam final MultipartFile multipartFile, @RequestParam final String wineDtoAsJson)
			throws IOException {
		logger.info("incomming POST request to create or update a wine {}", wineDtoAsJson);
		wineService.updateOrCreate(multipartFile, wineDtoAsJson);
		return "";
	}
}

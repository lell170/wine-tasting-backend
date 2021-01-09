package org.lell.winetasting.controller;

import org.lell.winetasting.model.WineDTO;
import org.lell.winetasting.service.PictureService;
import org.lell.winetasting.service.WineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/wine", produces = "application/json")
public final class WineController {

    private static final Logger logger = LoggerFactory.getLogger(WineController.class);

    private final WineService wineService;
    private final PictureService pictureService;

    public WineController(final WineService wineService, final PictureService pictureService) {
        this.wineService = wineService;
        this.pictureService = pictureService;
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<WineDTO>> getAllWines() {
        logger.info("received http request get all wines");
        return new ResponseEntity<>(wineService.getAllWines(), HttpStatus.OK);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Long> createWine(@RequestBody final WineDTO wineDTO) throws ParseException {
        logger.info("received http request CREATE new wine {} ", wineDTO);
        if (wineDTO.getId() != 0) {
            logger.error("Given object has an existing ID {}. Otherwise use the update endpoint instead", wineDTO.getId());
            throw new IllegalArgumentException("an error occurred while creating a new wine. id ist not empty!");
        }
        final long id = wineService.createWine(wineDTO);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}/json")
    public ResponseEntity<Long> updateWineData(@RequestBody final WineDTO wineDTO, @PathVariable("id") final long id)
            throws ParseException {
        if (wineDTO.getId() == 0) {
            logger.error("wine id for update is not given!");
            throw new IllegalArgumentException("an error occurred while updating the data. id is not given");
        }
        if (wineDTO.getId() != id) {
            logger.error("id {} from wineDto doesn't match id {} from PathVariable!", wineDTO.getId(), id);
            throw new IllegalArgumentException("an error occurred while updating the data: id doesnt match path variable");
        }
        logger.info("received http request UPDATE existing wine with id {}", wineDTO.getId());
        final long wineId = wineService.saveWine(wineService.convertWineDto(wineDTO));

        return new ResponseEntity<>(wineId, HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}/picture")
    public ResponseEntity<String> updateWinePicture(@RequestBody final MultipartFile pictureFile, @PathVariable("id") final long id)
            throws IOException {
        logger.info("received http request UPDATE picture by existing wine with id {}", id);
        return new ResponseEntity<>(pictureService.updatePicture(pictureFile, id), HttpStatus.OK);
    }
}

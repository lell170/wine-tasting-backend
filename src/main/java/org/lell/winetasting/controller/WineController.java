package org.lell.winetasting.controller;

import org.lell.winetasting.model.Wine;
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
import java.util.List;

@RestController
@RequestMapping(value = "/api/wine", produces = "application/json")
public class WineController {

    private static final Logger logger = LoggerFactory.getLogger(WineController.class);

    private final WineService wineService;
    private final PictureService pictureService;

    WineController(final WineService wineService, final PictureService pictureService) {
        this.wineService = wineService;
        this.pictureService = pictureService;
    }

    @GetMapping(path = "/all")
    public ResponseEntity<List<Wine>> getAllWines() {
        logger.info("received http request get all wines");
        return new ResponseEntity<>(wineService.getAllWines(), HttpStatus.OK);
    }

    @PostMapping(path = "/create")
    public long createWine(@RequestBody final WineDTO wineDTO) {
        logger.info("received http request CREATE new wine {} ", wineDTO);
        if (wineDTO.getId() != 0) {
            logger.error("Create Wine Data: Given object has an existed ID! Otherwise use the update endpoint!");
            throw new RuntimeException();
        }
        return wineService.createWine(wineDTO);
    }

    @PutMapping(path = "/update/{id}/json")
    public void updateWineData(@RequestBody final WineDTO wineDTO, @PathVariable("id") final long id) {
        if (wineDTO.getId() == 0) {
            logger.error("Update Wine Data: wine id for update is not given!");
            throw new RuntimeException();
        }
        if (wineDTO.getId() != id) {
            logger.error("Update Wine Data: id from wineDto doesn't match id from PathVariable!");
            throw new RuntimeException();
        }
        logger.info("received http request UPDATE existing wine with id {}", wineDTO.getId());
        wineService.updateWine(wineDTO);
    }

    @PutMapping(path = "/update/{id}/picture")
    public ResponseEntity<String> updateWinePicture(@RequestBody final MultipartFile pictureFile, @PathVariable("id") final long id)
            throws IOException {
        logger.info("received http request UPDATE picture by existing wine with id {}", id);
        return new ResponseEntity<>(pictureService.updatePicture(pictureFile, id), HttpStatus.OK);
    }
}

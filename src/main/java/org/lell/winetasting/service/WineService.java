package org.lell.winetasting.service;

import com.google.common.collect.Streams;
import org.lell.winetasting.model.CountryCode;
import org.lell.winetasting.model.Type;
import org.lell.winetasting.model.Wine;
import org.lell.winetasting.model.WineDTO;
import org.lell.winetasting.repository.WineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WineService {

    private static final Logger logger = LoggerFactory.getLogger(WineService.class);

    private final WineRepository wineRepository;

    public WineService(final WineRepository wineRepository) {
        this.wineRepository = wineRepository;
    }

    public List<Wine> getAllWines() {
        return Streams.stream(wineRepository.findByOrderByIdAsc()).collect(Collectors.toList());
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

    public void updateWine(final WineDTO wineDTO) {
        final Wine wine = this.parseDTO(wineDTO);
        this.saveWine(wine);
    }

    public long createWine(final WineDTO wineDTO) {
        final Wine wine = this.parseDTO(wineDTO);
        return this.saveWine(wine);
    }

    public long saveWine(final Wine wine) {
        if (wine.getCreationDate() == null) {
            wine.setCreationDate(LocalDateTime.now());
        }
        return wineRepository.save(wine).getId();
    }
}

package org.lell.winetasting.service;

import com.google.common.collect.Streams;
import org.lell.winetasting.model.CountryCode;
import org.lell.winetasting.model.Type;
import org.lell.winetasting.model.Wine;
import org.lell.winetasting.model.WineDTO;
import org.lell.winetasting.repository.WineRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public final class WineService {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS");

    private final WineRepository wineRepository;

    public WineService(final WineRepository wineRepository) {
        this.wineRepository = wineRepository;
    }

    public List<WineDTO> getAllWines() {
        return Streams.stream(wineRepository.findByOrderByIdAsc()).map(this::convertWine)
                .collect(Collectors.toList());
    }

    public Wine convertWineDto(final WineDTO wineDTO) throws ParseException {
        final Wine wine = new Wine();

        if (wineDTO.getCreated() != null) {
            final Date created = SIMPLE_DATE_FORMAT.parse(wineDTO.getCreated());
            wine.setCreated(created);
        }
        wine.setWineMaker(wineDTO.getWineMaker());
        wine.setPictureFileName(wineDTO.getPictureFileName());
        wine.setDescription(wineDTO.getDescription());
        wine.setCountryCode(CountryCode.valueOf(wineDTO.getCountryCode()));
        wine.setGrape(wineDTO.getGrape());
        wine.setId(wineDTO.getId());
        wine.setName(wineDTO.getName());
        wine.setYear(wineDTO.getYear());
        wine.setType(Type.valueOf(wineDTO.getType()));
        wine.setOverallRating(wineDTO.getOverallRating());

        return wine;
    }

    public WineDTO convertWine(final Wine wine) {
        final WineDTO wineDto = new WineDTO();

        if (wine.getCreated() != null) {
            wineDto.setCreated(convertDate(wine.getCreated()));
        }
        if (wine.getUpdated() != null) {
            wineDto.setUpdated(convertDate(wine.getUpdated()));
        }
        wineDto.setWineMaker(wine.getWineMaker());
        wineDto.setPictureFileName(wine.getPictureFileName());
        wineDto.setDescription(wine.getDescription());
        wineDto.setCountryCode(wine.getCountryCode().name());
        wineDto.setGrape(wine.getGrape());
        wineDto.setId(wine.getId());
        wineDto.setName(wine.getName());
        wineDto.setYear(wine.getYear());
        wineDto.setType(wine.getType().name());
        wineDto.setOverallRating(wine.getOverallRating());

        return wineDto;
    }

    public long createWine(final WineDTO wineDTO) throws ParseException {
        final Wine wine = this.convertWineDto(wineDTO);
        return this.saveWine(wine);
    }

    public long saveWine(final Wine wine) {
        return wineRepository.save(wine).getId();
    }

    private String convertDate(final Date date) {
        return SIMPLE_DATE_FORMAT.format(date);
    }
}

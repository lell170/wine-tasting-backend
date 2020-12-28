package org.lell.winetasting.model;

import java.io.File;

public class WineDTO {

	private long id;
	private String name;
	private String fileName;
	private String grape;
	private String creationDate;
	private String changeDate;
	private String countryCode;
	private String type;
	private String description;
	private String year;
	private String wineMaker;

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(final String fileName) {
		this.fileName = fileName;
	}

	public String getGrape() {
		return grape;
	}

	public void setGrape(final String grape) {
		this.grape = grape;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getYear() {
		return year;
	}

	public void setYear(final String year) {
		this.year = year;
	}

	public String getWineMaker() {
		return wineMaker;
	}

	public void setWineMaker(final String wineMaker) {
		this.wineMaker = wineMaker;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(final String creationDate) {
		this.creationDate = creationDate;
	}

	public String getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(final String changeDate) {
		this.changeDate = changeDate;
	}

	@Override
	public String toString() {
		return "WineDTO{" + "id=" + id + ", name='" + name + '\'' + ", fileName='" + fileName + '\'' + ", grape='" + grape +
				'\'' + ", creationDate=" + creationDate + ", changeDate=" + changeDate + ", countryCode='" + countryCode + '\'' +
				", type='" + type + '\'' + ", description='" + description + '\'' + ", year='" + year + '\'' + ", wineMaker='" +
				wineMaker + '\'';
	}
}

package org.lell.winetasting.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Wine {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String pictureFileName;
	private String grape;
	private LocalDateTime creationDate;
	private LocalDateTime changeDate;

	@Enumerated(EnumType.STRING)
	private CountryCode countryCode;
	private Type type;

	@Column(columnDefinition = "TEXT")
	private String description;
	private String year;
	private String wineMaker;

	public Wine() {

	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getPictureFileName() {
		return pictureFileName;
	}

	public void setPictureFileName(final String fileName) {
		this.pictureFileName = fileName;
	}

	public String getGrape() {
		return grape;
	}

	public void setGrape(final String grape) {
		this.grape = grape;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(final LocalDateTime importDate) {
		this.creationDate = importDate;
	}

	public LocalDateTime getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(final LocalDateTime changeDate) {
		this.changeDate = changeDate;
	}

	public CountryCode getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(final CountryCode countryCode) {
		this.countryCode = countryCode;
	}

	public Type getType() {
		return type;
	}

	public void setType(final Type type) {
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

	@Override
	public String toString() {
		return "Wine{" + "id=" + id + ", name='" + name + '\'' + ", fileName='" + pictureFileName + '\'' + ", grape='" + grape + '\'' +
				", importDate=" + creationDate + ", changeDate=" + changeDate + ", countryCode=" + countryCode + ", type=" + type +
				", description='" + description + '\'' + ", year='" + year + '\'' + ", wineMaker='" + wineMaker + '\'' + '}';
	}
}

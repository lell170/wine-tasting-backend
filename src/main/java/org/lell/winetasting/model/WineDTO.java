package org.lell.winetasting.model;

public final class WineDTO {

    private long id;
    private String name;
    private String pictureFileName;
    private String grape;
    private String created;
    private String updated;
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

    public String getPictureFileName() {
        return pictureFileName;
    }

    public void setPictureFileName(final String pictureFileName) {
        this.pictureFileName = pictureFileName;
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

    public String getCreated() {
        return created;
    }

    public void setCreated(final String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(final String updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "WineDTO{" + "id=" + id + ", name='" + name + '\'' + ", pictureFileName='" + pictureFileName + '\'' + ", grape='" + grape +
                '\'' + ", created='" + created + '\'' + ", updated='" + updated + '\'' + ", countryCode='" + countryCode + '\'' +
                ", type='" + type + '\'' + ", description='" + description + '\'' + ", year='" + year + '\'' + ", wineMaker='" + wineMaker +
                '\'' + '}';
    }
}

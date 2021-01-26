package org.lell.winetasting.model;

import com.google.common.base.Objects;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Entity
public final class Wine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String pictureFileName;
    private String grape;

    private Date created;
    private Date updated;

    @Enumerated(EnumType.STRING)
    private CountryCode countryCode;
    private Type type;

    @Column(columnDefinition = "TEXT")
    private String description;
    private String year;
    private String wineMaker;

    private String overallRating;

    public Wine() {
        // default constructor
    }

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }

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

    public void setPictureFileName(final String fileName) {
        this.pictureFileName = fileName;
    }

    public String getGrape() {
        return grape;
    }

    public void setGrape(final String grape) {
        this.grape = grape;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(final Date importDate) {
        this.created = importDate;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(final Date updated) {
        this.updated = updated;
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

    public String getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(final String overalRating) {
        this.overallRating = overalRating;
    }

    @Override
    public String toString() {
        return "Wine{" + "id=" + id + ", name='" + name + '\'' + ", fileName='" + pictureFileName + '\'' + ", grape='" + grape + '\'' +
                ", importDate=" + created + ", changeDate=" + updated + ", countryCode=" + countryCode + ", type=" + type +
                ", description='" + description + '\'' + ", year='" + year + '\'' + ", wineMaker='" + wineMaker + '\'' + '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Wine wine = (Wine) o;
        return id == wine.id && Objects.equal(name, wine.name) && Objects.equal(pictureFileName, wine.pictureFileName) &&
                Objects.equal(grape, wine.grape) && countryCode == wine.countryCode && type == wine.type &&
                Objects.equal(description, wine.description) && Objects.equal(year, wine.year) && Objects.equal(wineMaker, wine.wineMaker);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, pictureFileName, grape, created, updated, countryCode, type, description, year, wineMaker);
    }


}

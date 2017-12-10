package models;

import io.ebean.Finder;
import io.ebean.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import play.data.format.Formats;
import play.data.validation.Constraints;

import javax.persistence.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

@ApiModel
@Entity
@Table(name = "film")
public class FilmEntity extends Model {

    @ApiModelProperty(value = "Autoincrement value")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long filmId;

    @ApiModelProperty(position = 1, required = true, value = "title")
    @Constraints.Required(message = "title is required")
    private String title;

    @ApiModelProperty(position = 2, required = true, value = "description")
    private String description;

    @ApiModelProperty(position = 3, required = true, value = "releaseYear")
    @Constraints.Required(message = "releaseYear is required")
    private Long releaseYear;

    @ApiModelProperty(position = 4, required = true, value = "rentalDuration")
    @Constraints.Required(message = "rentalDuration is required")
    private Byte rentalDuration;

    @ApiModelProperty(position = 5, required = true, value = "rentalRate")
    @Constraints.Required(message = "rentalRate is required")
    private Double rentalRate;

    @ApiModelProperty(position = 6, required = true, value = "languageId")
    @Constraints.Required(message = "languageId is required")
    private Long languageId;

    @ApiModelProperty(position = 7, required = true, value = "languageId")
    @Constraints.Required(message = "originalLanguageId is required")
    private Long originalLanguageId;

    private Short length;
    private Double replacementCost;
    private String rating;
    private String specialFeatures;

    @Temporal(TemporalType.TIMESTAMP)
    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(updatable = false)
    private Timestamp createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updatedAt;


    @Column(name = "film_id", nullable = false)
    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    @Basic
    @Column(name = "title", nullable = false, length = 255)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "release_year", nullable = false)
    public Long getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Long releaseYear) {
        this.releaseYear = releaseYear;
    }

    @Basic
    @Column(name = "rental_duration", nullable = false)
    public Byte getRentalDuration() {
        return rentalDuration;
    }

    public void setRentalDuration(Byte rentalDuration) {
        this.rentalDuration = rentalDuration;
    }

    @Basic
    @Column(name = "rental_rate", nullable = false)
    public Double getRentalRate() {
        return rentalRate;
    }

    public void setRentalRate(Double rentalRate) {
        this.rentalRate = rentalRate;
    }

    @Basic
    @Column(name = "length", nullable = true)
    public Short getLength() {
        return length;
    }

    public void setLength(Short length) {
        this.length = length;
    }

    @Basic
    @Column(name = "replacement_cost", nullable = false, precision = 2)
    public Double getReplacementCost() {
        return replacementCost;
    }

    public void setReplacementCost(Double replacementCost) {
        this.replacementCost = replacementCost;
    }

    @Basic
    @Column(name = "rating")
    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Basic
    @Column(name = "language_id", nullable = false)
    public Long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Long languageId) {
        this.languageId = languageId;
    }

    @Basic
    @Column(name = "original_language_id", nullable = false)
    public Long getOriginalLanguageId() {
        return originalLanguageId;
    }

    public void setOriginalLanguageId(Long originalLanguageId) {
        this.originalLanguageId = originalLanguageId;
    }

    @Basic
    @Column(name = "special_features", nullable = true)
    public String getSpecialFeatures() {
        return specialFeatures;
    }

    public void setSpecialFeatures(String specialFeatures) {
        this.specialFeatures = specialFeatures;
    }

    @Basic
    @Column(name = "created_at", nullable = false)
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Basic
    @Column(name = "updated_at", nullable = false)
    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilmEntity that = (FilmEntity) o;

        if (filmId != null ? !filmId.equals(that.filmId) : that.filmId != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (releaseYear != null ? !releaseYear.equals(that.releaseYear) : that.releaseYear != null) return false;
        if (rentalDuration != null ? !rentalDuration.equals(that.rentalDuration) : that.rentalDuration != null)
            return false;
        if (rentalRate != null ? !rentalRate.equals(that.rentalRate) : that.rentalRate != null) return false;
        if (length != null ? !length.equals(that.length) : that.length != null) return false;
        if (replacementCost != null ? !replacementCost.equals(that.replacementCost) : that.replacementCost != null)
            return false;
        if (rating != null ? !rating.equals(that.rating) : that.rating != null) return false;
        if (languageId != null ? !languageId.equals(that.languageId) : that.languageId != null) return false;
        if (specialFeatures != null ? !specialFeatures.equals(that.specialFeatures) : that.specialFeatures != null)
            return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = filmId != null ? filmId.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (releaseYear != null ? releaseYear.hashCode() : 0);
        result = 31 * result + (rentalDuration != null ? rentalDuration.hashCode() : 0);
        result = 31 * result + (rentalRate != null ? rentalRate.hashCode() : 0);
        result = 31 * result + (length != null ? length.hashCode() : 0);
        result = 31 * result + (replacementCost != null ? replacementCost.hashCode() : 0);
        result = 31 * result + (languageId != null ? languageId.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (specialFeatures != null ? specialFeatures.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    /**
     * Query Builder
     */
    public static final Finder<Long, FilmEntity> finder = new Finder<>(FilmEntity.class);
}

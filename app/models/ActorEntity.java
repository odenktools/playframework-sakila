package models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

import io.ebean.*;
import io.swagger.annotations.ApiModelProperty;
import play.data.format.*;

import play.data.validation.Constraints;
import play.data.validation.Constraints.Validate;
import play.data.validation.Constraints.Validatable;
import play.data.validation.ValidationError;

import io.swagger.annotations.ApiModel;
import utils.Helpers;
import utils.validator.AlphabetNumericValidator;

@ApiModel
@Validate
@Entity
@Table(name = "actor")
public class ActorEntity extends Model implements Validatable<List<ValidationError>> {

    @ApiModelProperty(value = "Autoincrement value")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id", nullable = false)
    public Long actorId;

    @ApiModelProperty(position = 1, required = true, value = "firstName containing only lowercase letters or numbers")
    @Constraints.Pattern(value = "^[a-zA-Z0-9 \\\\._\\\\-]+$", message = "lastName containing only letters or number")
    @Constraints.Required(message = "firstName is required")
    @Constraints.MinLength(value = 2, message = "Minimum value 2")
    @Constraints.MaxLength(value = 45, message = "Maximum value 50")
    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @ApiModelProperty(position = 2, required = true, value = "lastName containing only letters or numbers")
    @Constraints.Required(message = "lastName is required")
    @Constraints.ValidateWith(AlphabetNumericValidator.class)
    @Constraints.MinLength(value = 2, message = "Minimum value 2")
    @Constraints.MaxLength(value = 45, message = "Maximum value 50")
    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;
	
    @Column(name = "images", columnDefinition = "TEXT")
    private String images;
	
    @Temporal(TemporalType.TIMESTAMP)
    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(updatable = false)
    private Timestamp createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updatedAt;

    public Long getActorId() {
        return actorId;
    }

    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
	
    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Query Builder
     */
    public static final Finder<Long, ActorEntity> finder = new Finder<>(ActorEntity.class);

    @Override
    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<>();
        if (this.firstName == null || Helpers.isEmpty(this.firstName)) {
            errors.add(new ValidationError("firstName", "Invalid firstName"));
        }
        if (this.lastName == null || Helpers.isEmpty(this.lastName)) {
            errors.add(new ValidationError("lastName", "Invalid lastName"));
        }
        return errors;
    }
}

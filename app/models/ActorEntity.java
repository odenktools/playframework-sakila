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

import javax.validation.groups.Default;

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
    private Long actorId;

    @ApiModelProperty(position = 1, required = true, value = "firstName containing only lowercase letters or numbers")
    @Constraints.Pattern(value = "^[a-zA-Z0-9 \\\\._\\\\-]+$", message = "lastName containing only letters or number")
    @Constraints.Required(message = "firstName is required")
    private String firstName;

    @ApiModelProperty(position = 2, required = true, value = "lastName containing only letters or numbers")
    @Constraints.Required(message = "lastName is required")
    @Constraints.ValidateWith(AlphabetNumericValidator.class)
    private String lastName;

    @Temporal(TemporalType.TIMESTAMP)
    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(updatable = false)
    private Timestamp createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updatedAt;

    @Id
    @Column(name = "actor_id", nullable = false)
    public Long getActorId() {
        return actorId;
    }

    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }

    @Basic
    @Column(name = "first_name", nullable = false, length = 45)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = false, length = 45)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

        ActorEntity that = (ActorEntity) o;

        if (actorId != null ? !actorId.equals(that.actorId) : that.actorId != null) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (updatedAt != null ? !updatedAt.equals(that.updatedAt) : that.updatedAt != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = actorId != null ? actorId.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }

    /**
     * Query Builder
     */
    public static final Finder<Long, ActorEntity> finder = new Finder<>(ActorEntity.class);

    @Override
    public List<ValidationError> validate() {
        List<ValidationError> errors = new ArrayList<ValidationError>();
        if (this.firstName == null || Helpers.isEmpty(this.firstName)) {
            errors.add(new ValidationError("firstName", "Invalid firstName"));
        }
        if (this.lastName == null || Helpers.isEmpty(this.lastName)) {
            errors.add(new ValidationError("lastName", "Invalid lastName"));
        }
        return errors;
    }
}

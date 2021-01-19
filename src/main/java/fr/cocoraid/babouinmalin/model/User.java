package fr.cocoraid.babouinmalin.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Table(name = "users" , uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
@Entity @Data
public class User {

    @Id
    @Column(name = "id", columnDefinition = "CHAR(36)")
    @Type(type="uuid-char")
    private UUID id;
    @NotBlank
    private String name, surname;
    @Email
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public User(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.email = email;
    }
    public User() {

    }
}

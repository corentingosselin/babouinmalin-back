package fr.cocoraid.babouinmalin.model.barter;

import lombok.Data;
import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Table(name = "barters")
@Entity @Data
public class Barter {

    @GeneratedValue @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    @NotBlank
    @Size(min = 6, max = 255)
    private String description;

    @Column(name = "desire")
    @NotBlank
    @Size(min = 6, max = 255)
    private String desire;

    @Column(name = "title")
    @NotBlank
    @Size(min=6,max=30)
    private String title;

    @NotBlank
    @Column(name="category_id")
    private int categoryID;

    @NotBlank
    @Size(min=36,max=36)
    @Column(name="user_id")
    @Type(type="uuid-char")
    private UUID user_id;

}

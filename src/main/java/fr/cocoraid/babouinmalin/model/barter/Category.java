package fr.cocoraid.babouinmalin.model.barter;

import lombok.Data;

import javax.persistence.*;

@Table(name = "categories")
@Entity
@Data
public class Category {

    @Id
    @Column(name = "id")
    private Integer id;
    private String categoryName;
    public Category() {

    }
}

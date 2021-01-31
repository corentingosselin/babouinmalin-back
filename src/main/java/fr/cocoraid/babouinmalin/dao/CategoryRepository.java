package fr.cocoraid.babouinmalin.dao;

import fr.cocoraid.babouinmalin.model.barter.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}

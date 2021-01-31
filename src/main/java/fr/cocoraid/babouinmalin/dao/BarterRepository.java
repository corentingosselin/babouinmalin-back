package fr.cocoraid.babouinmalin.dao;

import fr.cocoraid.babouinmalin.model.barter.Barter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BarterRepository extends JpaRepository<Barter, Long> {
    boolean existsById(Long id);

    @Query(value = "SELECT * FROM barters ORDER BY ID DESC LIMIT 18", nativeQuery = true)
    List<Barter> getLastAddedBarters();

}

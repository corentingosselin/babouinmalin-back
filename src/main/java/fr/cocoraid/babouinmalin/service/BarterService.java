package fr.cocoraid.babouinmalin.service;

import fr.cocoraid.babouinmalin.exceptions.BarterNotFoundException;
import fr.cocoraid.babouinmalin.model.barter.Barter;
import fr.cocoraid.babouinmalin.model.barter.BarterDetail;

import java.util.List;
import java.util.UUID;

public interface BarterService {

    List<BarterDetail> getBartersDetailed(UUID userID);
    List<BarterDetail> getBartersDetailed(int categoryID);
    List<BarterDetail> getLastAddedBarters();

    boolean isBarterExists(Long id);

    Barter createBarter(Barter barter);
    BarterDetail getBarterDetail(Long id) throws BarterNotFoundException;
    void deleteBarter(Long id) throws BarterNotFoundException ;

    int getBarterAmount();



}

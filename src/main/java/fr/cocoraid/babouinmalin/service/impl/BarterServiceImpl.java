package fr.cocoraid.babouinmalin.service.impl;

import fr.cocoraid.babouinmalin.dao.BarterRepository;
import fr.cocoraid.babouinmalin.exceptions.BarterNotFoundException;
import fr.cocoraid.babouinmalin.model.barter.Barter;
import fr.cocoraid.babouinmalin.model.barter.BarterDetail;
import fr.cocoraid.babouinmalin.service.BarterService;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service(value = "barterService")
public class BarterServiceImpl implements BarterService {

    @Autowired
    BarterRepository barterRepository;

    MinioClient minioClient = MinioClient.builder()
            .endpoint("http://127.0.0.1:9000")
            .credentials("root", "marsatak")
            .build();


    private BarterDetail retrieveBarterDetail(Barter b) {
        try {
            String url =
                    minioClient.getPresignedObjectUrl(
                            GetPresignedObjectUrlArgs.builder()
                                    .method(Method.GET)
                                    .bucket("barters")
                                    .object(b.getId().toString() + ".png")
                                    .build());
            BarterDetail bd = new BarterDetail(b, url);
            return bd;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BarterDetail(b,"https://www.richardfremont.com/wp-content/themes/trend/assets/img/empty/424x500.png");
    }

    @Override
    public List<BarterDetail> getBartersDetailed(UUID userID) {
        List<BarterDetail> barters = new ArrayList<>();
        barterRepository.findAll().stream().filter(b -> b.getUser_id().equals(userID)).forEach(b -> {
            barters.add(retrieveBarterDetail(b));
        });
        return barters;
    }

    @Override
    public List<BarterDetail> getBartersDetailed(int categoryID) {
        List<BarterDetail> barters = new ArrayList<>();
        barterRepository.findAll().stream().filter(b -> b.getCategoryID() == categoryID).forEach(b -> {
            barters.add(retrieveBarterDetail(b));
        });
        return barters;
    }

    @Override
    public List<BarterDetail> getLastAddedBarters() {
        List<BarterDetail> barters = new ArrayList<>();
        barterRepository.getLastAddedBarters().forEach(b -> {
            barters.add(retrieveBarterDetail(b));
        });
        return barters;
    }

    @Override
    public boolean isBarterExists(Long id) {
        return barterRepository.findById(id).isPresent();
    }

    @Override
    public Barter createBarter(Barter barter) {
        barterRepository.save(barter);
        return barter;
    }

    @Override
    public BarterDetail getBarterDetail(Long id) throws BarterNotFoundException {
        Barter b =  barterRepository.findById(id).orElseThrow(() -> new BarterNotFoundException(id.toString()));
        return retrieveBarterDetail(b);
    }

    @Override
    public void deleteBarter(Long id) throws BarterNotFoundException  {
        Barter b =  barterRepository.findById(id).orElseThrow(() -> new BarterNotFoundException(id.toString()));
        try {
            barterRepository.deleteById(b.getId());
            minioClient.removeObject(RemoveObjectArgs.builder().bucket("barters").object(b.getId() + ".png").build());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getBarterAmount() {
        return barterRepository.findAll().size();
    }
}

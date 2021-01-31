package fr.cocoraid.babouinmalin.controller;

import fr.cocoraid.babouinmalin.dao.BarterRepository;
import fr.cocoraid.babouinmalin.exceptions.BarterNotFoundException;
import fr.cocoraid.babouinmalin.model.barter.Barter;
import fr.cocoraid.babouinmalin.model.barter.BarterDetail;
import fr.cocoraid.babouinmalin.payload.response.MessageResponse;
import fr.cocoraid.babouinmalin.service.BarterService;
import io.minio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/barter")
public class BarterController {

    MinioClient minioClient = MinioClient.builder()
            .endpoint("http://127.0.0.1:9000")
            .credentials("root", "marsatak")
            .build();
    @Autowired
    BarterRepository barterRep;

    @Autowired
    BarterService barterService;

    @PostMapping("/register")
    public ResponseEntity<?> createBarter(@Valid @RequestPart("barter") Barter barter, @RequestPart("file") MultipartFile file) {
        Barter barterCreated = barterService.createBarter(barter);
        try {
            // Ensure the bucket exists.
            if(!minioClient.bucketExists(BucketExistsArgs.builder().bucket("barters").build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket("barters").build());
            }
            String format =  file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            String fileName = barterCreated.getId().toString() + "." + format;
            minioClient.putObject(
                    PutObjectArgs.builder().bucket("barters").object(fileName).stream(
                            file.getInputStream(), file.getSize(), -1)
                            .build());
        } catch(Exception e) {
            System.err.println("Error occurred: " + e);
            e.printStackTrace();
        }

        return ResponseEntity.ok(new MessageResponse("Le troc a bien été enregistré"));
    }

    @GetMapping("/user/{uuid}")
    public List<BarterDetail> getBartersByUser(@PathVariable(value = "uuid") UUID userId) {
        return barterService.getBartersDetailed(userId);
    }

    @GetMapping("/lastbarters")
    public List<BarterDetail> getLastBarters() {
        return barterService.getLastAddedBarters();
    }


    @GetMapping("/category/{id}")
    public List<BarterDetail> getBartersByCategory(@PathVariable(value = "id") Integer category) {
        return barterService.getBartersDetailed(category);
    }

    @GetMapping("/{id}")
    public BarterDetail getBarterDetailByCategory(@PathVariable(value = "id") Long id) throws BarterNotFoundException {
        return barterService.getBarterDetail(id);
    }

    @PostMapping("/{id}")
    public void deleteBarter(@PathVariable(value = "id") Long id) throws BarterNotFoundException {
        barterService.deleteBarter(id);
    }
}
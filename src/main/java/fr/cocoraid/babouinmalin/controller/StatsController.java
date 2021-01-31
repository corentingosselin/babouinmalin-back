package fr.cocoraid.babouinmalin.controller;

import fr.cocoraid.babouinmalin.model.Stats;
import fr.cocoraid.babouinmalin.service.BarterService;
import fr.cocoraid.babouinmalin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class StatsController {

    @Autowired
    UserService userService;

    @Autowired
    BarterService barterService;

    @GetMapping("/stats")
    public Stats getCategories() {
       return new Stats(userService.getUserAmount(),barterService.getBarterAmount());
    }


}

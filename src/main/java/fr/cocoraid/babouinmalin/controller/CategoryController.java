package fr.cocoraid.babouinmalin.controller;

import fr.cocoraid.babouinmalin.model.barter.Category;
import fr.cocoraid.babouinmalin.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public List<Category> getCategories() {
        return categoryService.getAllCategory();
    }

    @GetMapping(value="categories/{id}", produces="text/plain")
    public ResponseEntity<String> getCategoryName(@PathVariable(value = "id") Integer id) {
        return ResponseEntity.ok(categoryService.getCategoryName(id));
    }

}

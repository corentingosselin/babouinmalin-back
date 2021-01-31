package fr.cocoraid.babouinmalin.service.impl;

import fr.cocoraid.babouinmalin.dao.CategoryRepository;
import fr.cocoraid.babouinmalin.model.barter.Category;
import fr.cocoraid.babouinmalin.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "categoryService")
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository repo;

    @Override
    public List<Category> getAllCategory() {
        return repo.findAll();
    }

    @Override
    public String getCategoryName(int id) {
        return repo.getOne(id).getCategoryName();
    }
}

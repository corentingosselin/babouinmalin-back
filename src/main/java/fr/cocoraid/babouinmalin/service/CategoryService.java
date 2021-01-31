package fr.cocoraid.babouinmalin.service;

import fr.cocoraid.babouinmalin.model.barter.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategory();

    String getCategoryName(int id);

}

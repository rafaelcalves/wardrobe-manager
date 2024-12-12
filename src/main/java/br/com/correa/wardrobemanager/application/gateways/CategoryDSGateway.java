package br.com.correa.wardrobemanager.application.gateways;

import br.com.correa.wardrobemanager.domain.entities.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryDSGateway {
    Category createCategory(Category category);
    Optional<Category> getCategoryByCode(String categoryCode);
    List<Category> getAllCategories();
}

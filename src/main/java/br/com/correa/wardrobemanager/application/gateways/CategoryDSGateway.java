package br.com.correa.wardrobemanager.application.gateways;

import br.com.correa.wardrobemanager.domain.entities.Category;

public interface CategoryDSGateway {
    Category createCategory(Category category);
}

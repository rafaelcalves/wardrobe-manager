package br.com.correa.wardrobemanager.application.usecases.category;

import br.com.correa.wardrobemanager.application.gateways.CategoryDSGateway;
import br.com.correa.wardrobemanager.domain.entities.Category;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryCreation {
    private final CategoryDSGateway dsGateway;

    public Category create(Category category) {
        return dsGateway.createCategory(category);
    }
}

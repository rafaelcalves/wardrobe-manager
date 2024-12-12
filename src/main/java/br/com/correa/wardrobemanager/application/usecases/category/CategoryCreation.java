package br.com.correa.wardrobemanager.application.usecases.category;

import br.com.correa.wardrobemanager.application.exceptions.ElementCodeConflictException;
import br.com.correa.wardrobemanager.application.exceptions.ElementNotFoundException;
import br.com.correa.wardrobemanager.application.gateways.CategoryDSGateway;
import br.com.correa.wardrobemanager.domain.entities.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryCreation {
    private final CategoryDSGateway dsGateway;
    private final CategorySearch categorySearch;

    public Category create(Category category) throws ElementCodeConflictException {
        try {
            categorySearch.getByCode(category.getCode());
            throw new ElementCodeConflictException("Category code exists");
        } catch (ElementNotFoundException e) {
            return dsGateway.createCategory(category);
        }
    }
}

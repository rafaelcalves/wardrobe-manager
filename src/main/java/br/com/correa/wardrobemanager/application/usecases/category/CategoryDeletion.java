package br.com.correa.wardrobemanager.application.usecases.category;

import br.com.correa.wardrobemanager.application.exceptions.ElementNotFoundException;
import br.com.correa.wardrobemanager.application.gateways.CategoryDSGateway;
import br.com.correa.wardrobemanager.domain.entities.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryDeletion {
    private final CategoryDSGateway dsGateway;

    public Category delete(String code) throws ElementNotFoundException {
        return dsGateway.deleteCategory(code).orElseThrow(() -> new ElementNotFoundException("Category not found"));
    }
}
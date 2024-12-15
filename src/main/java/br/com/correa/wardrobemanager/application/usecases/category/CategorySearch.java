package br.com.correa.wardrobemanager.application.usecases.category;

import br.com.correa.wardrobemanager.application.exceptions.ElementNotFoundException;
import br.com.correa.wardrobemanager.application.gateways.CategoryDSGateway;
import br.com.correa.wardrobemanager.domain.entities.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategorySearch {
    private final CategoryDSGateway dsGateway;

    public Category getByCode(String code) throws ElementNotFoundException {
        return dsGateway.getCategoryByCode(code).orElseThrow(() -> new ElementNotFoundException("Category not found"));
    }

    public List<Category> getAll() {
        return dsGateway.getAllCategories();
    }
}

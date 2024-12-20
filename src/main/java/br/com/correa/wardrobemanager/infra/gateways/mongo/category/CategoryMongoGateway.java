package br.com.correa.wardrobemanager.infra.gateways.mongo.category;

import br.com.correa.wardrobemanager.application.gateways.CategoryDSGateway;
import br.com.correa.wardrobemanager.domain.entities.Category;
import br.com.correa.wardrobemanager.infra.persistence.mongo.category.CategoryDocument;
import br.com.correa.wardrobemanager.infra.persistence.mongo.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryMongoGateway implements CategoryDSGateway {
    private final CategoryRepository categoryRepository;
    private final CategoryDocumentMapper categoryDocumentMapper;
    
    @Override
    public Optional<Category> deleteCategory(String categoryCode) {
        CategoryDocument categoryDocument = categoryRepository.deleteByCode(categoryCode).orElse(null);
        return Optional.ofNullable(categoryDocumentMapper.toDomain(categoryDocument));
    }

    @Override
    public Category createCategory(Category category) {
        CategoryDocument document = categoryDocumentMapper.toDocument(category);
        return categoryDocumentMapper.toDomain(categoryRepository.save(document));
    }

    @Override
    public Optional<Category> getCategoryByCode(String categoryCode) {
        CategoryDocument categoryDocument = categoryRepository.findByCode(categoryCode).orElse(null);
        return Optional.ofNullable(categoryDocumentMapper.toDomain(categoryDocument));
    }

    @Override
    public List<Category> getAllCategories() {
        List<CategoryDocument> categoryDocumentList = Objects.requireNonNullElse(categoryRepository.findAll(), Collections.emptyList());
        return categoryDocumentMapper.toDomain(categoryDocumentList);
    }
}

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
        Optional<CategoryDocument> categoryDocument = categoryRepository.deleteByCode(categoryCode);
        return categoryDocument.map(categoryDocumentMapper::toDomain);
    }

    @Override
    public Optional<Category> editCategory(String categoryCode, Category category) {
        Optional<CategoryDocument> categoryDocument = categoryRepository.findByCode(categoryCode);
        if(categoryDocument.isEmpty()) return Optional.empty();
        CategoryDocument document = categoryDocumentMapper.toDocument(categoryDocument.get(), category);
        return Optional.of(categoryDocumentMapper.toDomain(categoryRepository.save(document)));
    }

    @Override
    public Category createCategory(Category category) {
        CategoryDocument document = categoryDocumentMapper.toDocument(category);
        return categoryDocumentMapper.toDomain(categoryRepository.save(document));
    }

    @Override
    public Optional<Category> getCategoryByCode(String categoryCode) {
        Optional<CategoryDocument> categoryDocument = categoryRepository.findByCode(categoryCode);
        return categoryDocument.map(categoryDocumentMapper::toDomain);
    }

    @Override
    public List<Category> getAllCategories() {
        List<CategoryDocument> categoryDocumentList = Objects.requireNonNullElse(categoryRepository.findAll(), Collections.emptyList());
        return categoryDocumentMapper.toDomain(categoryDocumentList);
    }
}

package br.com.correa.wardrobemanager.infra.gateways.mongo.category;

import br.com.correa.wardrobemanager.application.gateways.CategoryDSGateway;
import br.com.correa.wardrobemanager.domain.entities.Category;
import br.com.correa.wardrobemanager.infra.persistence.mongo.category.CategoryDocument;
import br.com.correa.wardrobemanager.infra.persistence.mongo.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryMongoGateway implements CategoryDSGateway {
    private final CategoryRepository categoryRepository;
    private final CategoryDocumentMapper categoryDocumentMapper;

    @Override
    public Category createCategory(Category category) {
        CategoryDocument document = categoryDocumentMapper.toDocument(category);
        return categoryDocumentMapper.toDomain(categoryRepository.save(document));
    }
}

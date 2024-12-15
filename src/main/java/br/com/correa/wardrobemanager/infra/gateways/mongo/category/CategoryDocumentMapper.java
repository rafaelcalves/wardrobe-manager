package br.com.correa.wardrobemanager.infra.gateways.mongo.category;

import br.com.correa.wardrobemanager.domain.entities.Category;
import br.com.correa.wardrobemanager.infra.persistence.mongo.category.CategoryDocument;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryDocumentMapper {
    CategoryDocument toDocument(Category category);
    Category toDomain(CategoryDocument categoryDocument);

    List<Category> toDomain(List<CategoryDocument> categoryDocumentList);
}

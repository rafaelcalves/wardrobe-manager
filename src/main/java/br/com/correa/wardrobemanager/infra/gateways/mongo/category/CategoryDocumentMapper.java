package br.com.correa.wardrobemanager.infra.gateways.mongo.category;

import br.com.correa.wardrobemanager.domain.entities.Category;
import br.com.correa.wardrobemanager.infra.persistence.mongo.category.CategoryDocument;
import br.com.correa.wardrobemanager.config.MapStructConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;


@Mapper(config = MapStructConfig.class)
public interface CategoryDocumentMapper {
    CategoryDocument toDocument(Category category);
    CategoryDocument toDocument(@MappingTarget CategoryDocument target, Category source);
    Category toDomain(CategoryDocument categoryDocument);

    List<Category> toDomain(List<CategoryDocument> categoryDocumentList);
}

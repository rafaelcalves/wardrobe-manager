package br.com.correa.wardrobemanager.infra.controller.category;

import br.com.correa.wardrobemanager.domain.entities.Category;
import br.com.correa.wardrobemanager.config.MapStructConfig;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapStructConfig.class)
public interface CategoryDtoMapper {
    CategoryDto toDto(Category category);
    Category toDomain(CategoryDto categoryDto);

    List<CategoryDto> toDto(List<Category> categoryList);
}

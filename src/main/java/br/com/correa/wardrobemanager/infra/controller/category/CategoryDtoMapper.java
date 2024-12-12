package br.com.correa.wardrobemanager.infra.controller.category;

import br.com.correa.wardrobemanager.domain.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryDtoMapper {
    CategoryDto toDto(Category category);
    Category toDomain(CategoryDto categoryDto);

    List<CategoryDto> toDto(List<Category> categoryList);
}

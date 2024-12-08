package br.com.correa.wardrobemanager.infra.controller.category;

import br.com.correa.wardrobemanager.application.usecases.category.CategoryCreation;
import br.com.correa.wardrobemanager.domain.entities.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryCreation categoryCreation;
    private final CategoryDtoMapper categoryDtoMapper;

    @PostMapping
    public CategoryDto create(@RequestBody CategoryDto categoryDto) {
        Category domain = categoryDtoMapper.toDomain(categoryDto);
        return categoryDtoMapper.toDto(categoryCreation.create(domain));
    }
}

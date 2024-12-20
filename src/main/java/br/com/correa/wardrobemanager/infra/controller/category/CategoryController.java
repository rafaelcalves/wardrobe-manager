package br.com.correa.wardrobemanager.infra.controller.category;

import br.com.correa.wardrobemanager.application.exceptions.ElementCodeConflictException;
import br.com.correa.wardrobemanager.application.exceptions.ElementNotFoundException;
import br.com.correa.wardrobemanager.application.usecases.category.CategoryCreation;
import br.com.correa.wardrobemanager.application.usecases.category.CategoryDeletion;
import br.com.correa.wardrobemanager.application.usecases.category.CategorySearch;
import br.com.correa.wardrobemanager.domain.entities.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryCreation categoryCreation;
    private final CategorySearch categorySearch;
    private final CategoryDeletion categoryDeletion;
    private final CategoryDtoMapper categoryDtoMapper;

    @PostMapping
    public CategoryDto create(@RequestBody CategoryDto categoryDto) throws ElementCodeConflictException {
        Category domain = categoryDtoMapper.toDomain(categoryDto);
        Category result = categoryCreation.create(domain);
        return categoryDtoMapper.toDto(result);
    }

    @GetMapping("/{code}")
    public CategoryDto getByCode(@PathVariable String code) throws ElementNotFoundException {
        return categoryDtoMapper.toDto(categorySearch.getByCode(code));
    }

    @GetMapping
    public List<CategoryDto> getAll() {
        return categoryDtoMapper.toDto(categorySearch.getAll());
    }

    @DeleteMapping("/{code}")
    public CategoryDto delete(@PathVariable String code) throws ElementNotFoundException {
        return categoryDtoMapper.toDto(categoryDeletion.delete(code));
    }
}

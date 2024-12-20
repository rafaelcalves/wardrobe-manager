package br.com.correa.wardrobemanager.infra.controller.category;

import br.com.correa.wardrobemanager.application.exceptions.ElementCodeConflictException;
import br.com.correa.wardrobemanager.application.exceptions.ElementNotFoundException;
import br.com.correa.wardrobemanager.application.exceptions.InvalidElementException;
import br.com.correa.wardrobemanager.application.usecases.category.CategoryCreation;
import br.com.correa.wardrobemanager.application.usecases.category.CategoryDeletion;
import br.com.correa.wardrobemanager.application.usecases.category.CategorySearch;
import br.com.correa.wardrobemanager.config.ObjectMapperConfig;
import br.com.correa.wardrobemanager.domain.entities.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.hosuaby.inject.resources.junit.jupiter.GivenJsonResource;
import io.hosuaby.inject.resources.junit.jupiter.TestWithResources;
import io.hosuaby.inject.resources.junit.jupiter.WithJacksonMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@TestWithResources
@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {
    public static final String CATEGORY_CODE = "category_code";
    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryCreation categoryCreation;
    @Mock
    private CategorySearch categorySearch;
    @Mock
    private CategoryDeletion categoryDeletion;

    @Spy
    private CategoryDtoMapper categoryDtoMapper = new CategoryDtoMapperImpl();

    @WithJacksonMapper
    ObjectMapper mapper = ObjectMapperConfig.getObjectMapper();
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/category.json")
    Category category;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/category.json")
    CategoryDto categoryDto;

    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/categoryList.json")
    List<Category> categoryList;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/categoryList.json")
    List<CategoryDto> categoryDtoList;

    @Test
    void shouldRedirectToUseCaseAsDomainValue() throws ElementCodeConflictException {
        Mockito.when(categoryCreation.create(category)).thenReturn(category);

        CategoryDto result = categoryController.create(categoryDto);

        Assertions.assertEquals(categoryDto, result);
    }
    
    @Test
    void shouldReturnCategoryAsDto() throws ElementNotFoundException {
        Mockito.when(categorySearch.getByCode(CATEGORY_CODE)).thenReturn(category);
        CategoryDto result = categoryController.getByCode(CATEGORY_CODE);

        Assertions.assertEquals(categoryDto, result);
    }

    @Test
    void shouldReturnCategoryListAsDomain() {
        Mockito.when(categorySearch.getAll()).thenReturn(categoryList);
        List<CategoryDto> result = categoryController.getAll();

        Assertions.assertArrayEquals(categoryDtoList.toArray(), result.toArray());
    }

    @Test
    void shouldRedirectToDeleteUseCaseAndReturnDeletedElementIfSuccess() throws ElementNotFoundException {
        Mockito.when(categoryDeletion.delete(CATEGORY_CODE)).thenReturn(category);
        CategoryDto result = categoryController.delete(CATEGORY_CODE);

        Assertions.assertEquals(categoryDto, result);
        Mockito.verify(categoryDeletion).delete(CATEGORY_CODE);
    }

    @Test
    void shouldRedirectToDeleteUseCaseAndThrowException() throws ElementNotFoundException {
        Mockito.when(categoryDeletion.delete(CATEGORY_CODE)).thenThrow(ElementNotFoundException.class);
        Assertions.assertThrows(ElementNotFoundException.class, () -> categoryController.delete(CATEGORY_CODE));

        Mockito.verify(categoryDeletion).delete(CATEGORY_CODE);
    }

    @Test
    void shouldRefuseNullSource() {
        Assertions.assertThrows(InvalidElementException.class, () -> categoryController.create(null));
    }
}

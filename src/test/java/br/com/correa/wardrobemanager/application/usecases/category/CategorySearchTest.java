package br.com.correa.wardrobemanager.application.usecases.category;

import br.com.correa.wardrobemanager.application.exceptions.ElementNotFoundException;
import br.com.correa.wardrobemanager.application.gateways.CategoryDSGateway;
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
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@TestWithResources
@ExtendWith(MockitoExtension.class)
public class CategorySearchTest {
    public static final String CATEGORY_CODE = "category_code";
    
    @InjectMocks
    private CategorySearch categorySearch;
    @Mock
    private CategoryDSGateway categoryDSGateway;

    @WithJacksonMapper
    ObjectMapper mapper = ObjectMapperConfig.getObjectMapper();
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/category.json")
    Category category;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/categoryList.json")
    List<Category> categoryList;

    @Test
    void shouldReturnCategoryMatchingTheCode() throws ElementNotFoundException {
        Mockito.when(categoryDSGateway.getCategoryByCode(CATEGORY_CODE)).thenReturn(Optional.of(category));

        Category result = categorySearch.getByCode(CATEGORY_CODE);

        Assertions.assertEquals(category, result);
    }

    @Test
    void shouldReturnExceptionIfCategoryNotFound() {
        Mockito.when(categoryDSGateway.getCategoryByCode(CATEGORY_CODE)).thenReturn(Optional.empty());

        Assertions.assertThrows(ElementNotFoundException.class, () -> categorySearch.getByCode(CATEGORY_CODE));
    }

    @Test
    void shouldReturnAllCategorys() {
        Mockito.when(categoryDSGateway.getAllCategories()).thenReturn(categoryList);

        List<Category> result = categorySearch.getAll();
        Assertions.assertArrayEquals(categoryList.toArray(), result.toArray());
    }
}

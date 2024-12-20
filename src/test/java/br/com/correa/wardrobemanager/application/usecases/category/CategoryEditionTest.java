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

import java.util.Optional;

@TestWithResources
@ExtendWith(MockitoExtension.class)
class CategoryEditionTest {
    public static final String CATEGORY_CODE = "category_code";
    @InjectMocks
    private CategoryEdition categoryEdition;

    @Mock
    private CategoryDSGateway categoryDSGateway;

    @WithJacksonMapper
    ObjectMapper mapper = ObjectMapperConfig.getObjectMapper();
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/category.json")
    Category category;

    @Test
    void shouldReturnCategoryMatchingTheCode() throws ElementNotFoundException {
        Mockito.when(categoryDSGateway.editCategory(CATEGORY_CODE, category)).thenReturn(Optional.of(category));

        Category result = categoryEdition.edit(CATEGORY_CODE, category);

        Assertions.assertEquals(category, result);
    }

    @Test
    void shouldReturnExceptionIfCategoryNotFound() {
        Mockito.when(categoryDSGateway.editCategory(CATEGORY_CODE, category)).thenReturn(Optional.empty());

        Assertions.assertThrows(ElementNotFoundException.class, () -> categoryEdition.edit(CATEGORY_CODE, category));
    }
}

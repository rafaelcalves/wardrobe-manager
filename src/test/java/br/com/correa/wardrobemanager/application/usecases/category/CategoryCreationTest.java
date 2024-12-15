package br.com.correa.wardrobemanager.application.usecases.category;

import br.com.correa.wardrobemanager.application.exceptions.ElementCodeConflictException;
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

@TestWithResources
@ExtendWith(MockitoExtension.class)
class CategoryCreationTest {
    @Mock
    private CategoryDSGateway categoryDSGateway;
    @InjectMocks
    private CategoryCreation categoryCreation;
    @Mock
    private CategorySearch categorySearch;

    @WithJacksonMapper
    ObjectMapper mapper = ObjectMapperConfig.getObjectMapper();
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/category.json")
    Category category;

    @Test
    void categoryCreationShouldReturnReceivedCategoryAndCallGateway() throws ElementCodeConflictException, ElementNotFoundException {
        Mockito.when(categorySearch.getByCode(Mockito.any())).thenThrow(ElementNotFoundException.class);
        Mockito.when(categoryDSGateway.createCategory(Mockito.any())).thenReturn(category);

        var result = categoryCreation.create(category);

        Mockito.verify(categoryDSGateway).createCategory(category);
        Assertions.assertEquals(category, result);
    }

    @Test
    void shouldThrowExceptionIfElementCodeExists() throws ElementNotFoundException {
        Mockito.when(categorySearch.getByCode(Mockito.any())).thenReturn(category);

        Assertions.assertThrows(ElementCodeConflictException.class, () -> categoryCreation.create(category));
    }
}

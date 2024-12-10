package br.com.correa.wardrobemanager.infra.controller.category;

import br.com.correa.wardrobemanager.application.usecases.category.CategoryCreation;
import br.com.correa.wardrobemanager.config.ObjectMapperConfig;
import br.com.correa.wardrobemanager.domain.entities.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.hosuaby.inject.resources.junit.jupiter.GivenJsonResource;
import io.hosuaby.inject.resources.junit.jupiter.WithJacksonMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {
    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryCreation categoryCreation;

    @Spy
    private CategoryDtoMapper categoryDtoMapper = new CategoryDtoMapperImpl();

    @WithJacksonMapper
    ObjectMapper mapper = ObjectMapperConfig.getObjectMapper();
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/category.json")
    Category category;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/category.json")
    CategoryDto categoryDto;

    @Test
    void shouldRedirectToUseCaseAsDomainValue() {
        Mockito.when(categoryCreation.create(category)).thenReturn(category);

        CategoryDto result = categoryController.create(categoryDto);

        Assertions.assertEquals(categoryDto, result);
    }
}

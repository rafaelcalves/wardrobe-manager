package br.com.correa.wardrobemanager.infra.gateways.mongo.category;

import br.com.correa.wardrobemanager.config.ObjectMapperConfig;
import br.com.correa.wardrobemanager.domain.entities.Category;
import br.com.correa.wardrobemanager.infra.persistence.mongo.category.CategoryDocument;
import br.com.correa.wardrobemanager.infra.persistence.mongo.category.CategoryRepository;
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
import java.util.Optional;

@TestWithResources
@ExtendWith(MockitoExtension.class)
class CategoryMongoGatewayTest {
    public static final String CATEGORY_CODE = "category_code";
    @InjectMocks
    private CategoryMongoGateway categoryMongoGateway;

    @Mock
    private CategoryRepository categoryRepository;
    @Spy
    private CategoryDocumentMapper categoryDocumentMapper = new CategoryDocumentMapperImpl();

    @WithJacksonMapper
    ObjectMapper mapper = ObjectMapperConfig.getObjectMapper();
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/category.json")
    Category category;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/category.json")
    CategoryDocument categoryDocumentInput;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/persistence/mongo/category/categoryDocument.json")
    CategoryDocument categoryDocumentOutput;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/persistence/mongo/category/categoryDocumentList.json")
    List<CategoryDocument> categoryDocumentList;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/categoryList.json")
    List<Category> categoryList;

    @Test
    void shouldRedirectEntityAsDocumentToRepository() {
        Mockito.when(categoryRepository.save(categoryDocumentInput)).thenReturn(categoryDocumentOutput);

        Category result = categoryMongoGateway.createCategory(category);
        Assertions.assertEquals(category, result);
        Mockito.verify(categoryRepository).save(categoryDocumentInput);
    }


    @Test
    void shouldReturnCategoryAsDomain() {
        Mockito.when(categoryRepository.findByCode(CATEGORY_CODE)).thenReturn(Optional.of(categoryDocumentOutput));
        Optional<Category> result = categoryMongoGateway.getCategoryByCode(CATEGORY_CODE);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(category, result.get());
    }

    @Test
    void shouldReturnCategoryListAsDomain() {
        Mockito.when(categoryRepository.findAll()).thenReturn(categoryDocumentList);
        List<Category> result = categoryMongoGateway.getAllCategories();

        Assertions.assertArrayEquals(categoryList.toArray(), result.toArray());
    }

    @Test
    void shouldReturnEmptyListIfNullOnRepository() {
        Mockito.when(categoryRepository.findAll()).thenReturn(null);
        List<Category> result = categoryMongoGateway.getAllCategories();

        Assertions.assertTrue(result.isEmpty());
    }
    
    @Test
    void shouldReturnDeletedCategory() {
        Mockito.when(categoryRepository.deleteByCode(CATEGORY_CODE)).thenReturn(Optional.of(categoryDocumentOutput));
        Optional<Category> result = categoryMongoGateway.deleteCategory(CATEGORY_CODE);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(category, result.get());
    }

    @Test
    void deleteShouldReturnOptionalEmptyIfDoesntExists() {
        Mockito.when(categoryRepository.deleteByCode(CATEGORY_CODE)).thenReturn(Optional.empty());
        Optional<Category> result = categoryMongoGateway.deleteCategory(CATEGORY_CODE);

        Assertions.assertFalse(result.isPresent());
    }
}

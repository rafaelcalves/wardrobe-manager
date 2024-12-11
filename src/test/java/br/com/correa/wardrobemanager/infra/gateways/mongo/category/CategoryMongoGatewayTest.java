package br.com.correa.wardrobemanager.infra.gateways.mongo.category;

import br.com.correa.wardrobemanager.config.ObjectMapperConfig;
import br.com.correa.wardrobemanager.domain.entities.Category;
import br.com.correa.wardrobemanager.infra.persistence.mongo.category.CategoryDocument;
import br.com.correa.wardrobemanager.infra.persistence.mongo.category.CategoryRepository;
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
class CategoryMongoGatewayTest {
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

    @Test
    void shouldRedirectEntityAsDocumentToRepository() {
        Mockito.when(categoryRepository.save(categoryDocumentInput)).thenReturn(categoryDocumentOutput);

        Category result = categoryMongoGateway.createCategory(category);
        Assertions.assertEquals(category, result);
        Mockito.verify(categoryRepository).save(categoryDocumentInput);
    }
}

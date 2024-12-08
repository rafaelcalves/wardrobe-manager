package br.com.correa.wardrobemanager.infra.gateways.mongo.brand;

import br.com.correa.wardrobemanager.ObjectMapperConfig;
import br.com.correa.wardrobemanager.domain.entities.Brand;
import br.com.correa.wardrobemanager.infra.persistence.mongo.brand.BrandDocument;
import br.com.correa.wardrobemanager.infra.persistence.mongo.brand.BrandRepository;
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
class BrandMongoGatewayTest {
    @InjectMocks
    private BrandMongoGateway brandMongoGateway;

    @Mock
    private BrandRepository brandRepository;
    @Spy
    private BrandDocumentMapper brandDocumentMapper = new BrandDocumentMapperImpl();

    @WithJacksonMapper
    ObjectMapper mapper = ObjectMapperConfig.getObjectMapper();
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/brand.json")
    Brand brand;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/brand.json")
    BrandDocument brandDocumentInput;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/persistence/mongo/brand.json")
    BrandDocument brandDocumentOutput;

    @Test
    void shouldRedirectEntityAsDocumentToRepository() {
        Mockito.when(brandRepository.save(brandDocumentInput)).thenReturn(brandDocumentOutput);

        Brand result = brandMongoGateway.createBrand(brand);
        Assertions.assertEquals(brand, result);
        Mockito.verify(brandRepository).save(brandDocumentInput);
    }
}

package br.com.correa.wardrobemanager.infra.gateways.mongo.brand;

import br.com.correa.wardrobemanager.config.ObjectMapperConfig;
import br.com.correa.wardrobemanager.domain.entities.Brand;
import br.com.correa.wardrobemanager.infra.persistence.mongo.brand.BrandDocument;
import br.com.correa.wardrobemanager.infra.persistence.mongo.brand.BrandRepository;
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
class BrandMongoGatewayTest {
    public static final String BRAND_CODE = "brand_code";
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
    @GivenJsonResource("json/br/com/correa/wardrobemanager/persistence/mongo/brand/brandDocument.json")
    BrandDocument brandDocumentOutput;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/persistence/mongo/brand/brandDocumentList.json")
    List<BrandDocument> brandDocumentList;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/brandList.json")
    List<Brand> brandList;

    @Test
    void shouldRedirectEntityAsDocumentToRepository() {
        Mockito.when(brandRepository.save(brandDocumentInput)).thenReturn(brandDocumentOutput);

        Brand result = brandMongoGateway.createBrand(brand);
        Assertions.assertEquals(brand, result);
        Mockito.verify(brandRepository).save(brandDocumentInput);
    }

    @Test
    void shouldReturnBrandAsDomain() {
        Mockito.when(brandRepository.findByCode(BRAND_CODE)).thenReturn(Optional.of(brandDocumentOutput));
        Optional<Brand> result = brandMongoGateway.getBrandByCode(BRAND_CODE);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(brand, result.get());
    }

    @Test
    void shouldReturnBrandListAsDomain() {
        Mockito.when(brandRepository.findAll()).thenReturn(brandDocumentList);
        List<Brand> result = brandMongoGateway.getAllBrands();

        Assertions.assertArrayEquals(brandList.toArray(), result.toArray());
    }

    @Test
    void shouldReturnEmptyListIfNullOnRepository() {
        Mockito.when(brandRepository.findAll()).thenReturn(null);
        List<Brand> result = brandMongoGateway.getAllBrands();

        Assertions.assertTrue(result.isEmpty());
    }
}

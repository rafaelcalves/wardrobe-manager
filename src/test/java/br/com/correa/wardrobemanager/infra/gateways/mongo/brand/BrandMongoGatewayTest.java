package br.com.correa.wardrobemanager.infra.gateways.mongo.brand;

import br.com.correa.wardrobemanager.application.exceptions.InvalidElementException;
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
    @GivenJsonResource("json/br/com/correa/wardrobemanager/persistence/mongo/brand/brandDocument_old.json")
    BrandDocument brandDocumentOutputOld;
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
    void searchShouldReturnOptionalEmptyIfDoesntExists() {
        Mockito.when(brandRepository.findByCode(BRAND_CODE)).thenReturn(Optional.empty());
        Optional<Brand> result = brandMongoGateway.getBrandByCode(BRAND_CODE);

        Assertions.assertFalse(result.isPresent());
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

    @Test
    void shouldReturnDeletedBrand() {
        Mockito.when(brandRepository.deleteByCode(BRAND_CODE)).thenReturn(Optional.of(brandDocumentOutput));
        Optional<Brand> result = brandMongoGateway.deleteBrand(BRAND_CODE);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(brand, result.get());
    }

    @Test
    void deleteShouldReturnOptionalEmptyIfDoesntExists() {
        Mockito.when(brandRepository.deleteByCode(BRAND_CODE)).thenReturn(Optional.empty());
        Optional<Brand> result = brandMongoGateway.deleteBrand(BRAND_CODE);

        Assertions.assertFalse(result.isPresent());
    }

    @Test
    void shouldReturnEditedBrandAsDomain() {
        Mockito.when(brandRepository.findByCode(BRAND_CODE)).thenReturn(Optional.of(brandDocumentOutputOld));
        Mockito.when(brandRepository.save(brandDocumentOutputOld)).thenReturn(brandDocumentOutput);
        Optional<Brand> result = brandMongoGateway.editBrand(BRAND_CODE, brand);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(brand, result.get());
    }

    @Test
    void editShouldReturnOptionalEmptyIfDoesntExists() {
        Mockito.when(brandRepository.findByCode(BRAND_CODE)).thenReturn(Optional.empty());
        Optional<Brand> result = brandMongoGateway.editBrand(BRAND_CODE, brand);

        Assertions.assertTrue(result.isEmpty());
        Mockito.verify(brandRepository, Mockito.times(0)).save(brandDocumentOutput);
    }

    @Test
    void shouldRefuseNullSource() {
        Assertions.assertThrows(InvalidElementException.class, () -> brandMongoGateway.createBrand(null));
    }
}

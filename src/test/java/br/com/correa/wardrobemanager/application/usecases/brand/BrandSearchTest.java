package br.com.correa.wardrobemanager.application.usecases.brand;

import br.com.correa.wardrobemanager.ObjectMapperConfig;
import br.com.correa.wardrobemanager.application.exceptions.ElementNotFoundException;
import br.com.correa.wardrobemanager.application.gateways.BrandDSGateway;
import br.com.correa.wardrobemanager.domain.entities.Brand;
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
class BrandSearchTest {
    public static final String BRAND_CODE = "brand_code";
    @InjectMocks
    private BrandSearch brandSearch;

    @Mock
    private BrandDSGateway brandDSGateway;

    @WithJacksonMapper
    ObjectMapper mapper = ObjectMapperConfig.getObjectMapper();
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/brand.json")
    Brand brand;

    @Test
    void shouldReturnBrandMatchingTheCode() throws ElementNotFoundException {
        Mockito.when(brandDSGateway.getBrandByCode(BRAND_CODE)).thenReturn(Optional.of(brand));

        Brand result = brandSearch.getByCode(BRAND_CODE);

        Assertions.assertEquals(brand, result);
    }

    @Test
    void shouldReturnExceptionIfBrandNotFound() throws ElementNotFoundException {
        Mockito.when(brandDSGateway.getBrandByCode(BRAND_CODE)).thenReturn(Optional.empty());

        Assertions.assertThrows(ElementNotFoundException.class, () -> brandSearch.getByCode(BRAND_CODE));
    }
}

package br.com.correa.wardrobemanager.application.usecases.brand;

import br.com.correa.wardrobemanager.ObjectMapperConfig;
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

@TestWithResources
@ExtendWith(MockitoExtension.class)
class BrandCreationTest {
    @Mock
    private BrandDSGateway brandDSGateway;
    @InjectMocks
    private BrandCreation brandCreation;

    @WithJacksonMapper
    ObjectMapper mapper = ObjectMapperConfig.getObjectMapper();
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/brand.json")
    Brand brand;

    @Test
    void brandCreationShouldReturnReceivedBrandAndCallGateway() {
        Mockito.when(brandDSGateway.createBrand(Mockito.any())).thenReturn(brand);

        var result = brandCreation.create(brand);

        Mockito.verify(brandDSGateway).createBrand(brand);
        Assertions.assertEquals(brand, result);
    }
}

package br.com.correa.wardrobemanager.infra.controller.brand;

import br.com.correa.wardrobemanager.ObjectMapperConfig;
import br.com.correa.wardrobemanager.application.exceptions.ElementCodeConflictException;
import br.com.correa.wardrobemanager.application.usecases.brand.BrandCreation;
import br.com.correa.wardrobemanager.domain.entities.Brand;
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
class BrandControllerTest {
    @InjectMocks
    private BrandController brandController;

    @Mock
    private BrandCreation brandCreation;

    @Spy
    private BrandDtoMapper brandDtoMapper = new BrandDtoMapperImpl();

    @WithJacksonMapper
    ObjectMapper mapper = ObjectMapperConfig.getObjectMapper();
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/brand.json")
    Brand brand;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/brand.json")
    BrandDto brandDto;

    @Test
    void shouldRedirectToUseCaseAsDomainValue() throws ElementCodeConflictException {
        Mockito.when(brandCreation.create(brand)).thenReturn(brand);

        BrandDto result = brandController.create(brandDto);

        Assertions.assertEquals(brandDto, result);
    }
}

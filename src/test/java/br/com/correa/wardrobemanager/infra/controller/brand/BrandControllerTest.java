package br.com.correa.wardrobemanager.infra.controller.brand;

import br.com.correa.wardrobemanager.application.exceptions.ElementCodeConflictException;
import br.com.correa.wardrobemanager.application.exceptions.ElementNotFoundException;
import br.com.correa.wardrobemanager.application.usecases.brand.BrandCreation;
import br.com.correa.wardrobemanager.application.usecases.brand.BrandSearch;
import br.com.correa.wardrobemanager.config.ObjectMapperConfig;
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
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@TestWithResources
@ExtendWith(MockitoExtension.class)
class BrandControllerTest {
    public static final String BRAND_CODE = "brand_code";
    @InjectMocks
    private BrandController brandController;

    @Mock
    private BrandCreation brandCreation;
    @Mock
    private BrandSearch brandSearch;

    @Spy
    private BrandDtoMapper brandDtoMapper = new BrandDtoMapperImpl();

    @WithJacksonMapper
    ObjectMapper mapper = ObjectMapperConfig.getObjectMapper();
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/brand.json")
    Brand brand;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/brand.json")
    BrandDto brandDto;

    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/brandList.json")
    List<Brand> brandList;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/brandList.json")
    List<BrandDto> brandDtoList;

    @Test
    void shouldRedirectToUseCaseAsDomainValue() throws ElementCodeConflictException {
        Mockito.when(brandCreation.create(brand)).thenReturn(brand);

        BrandDto result = brandController.create(brandDto);

        Assertions.assertEquals(brandDto, result);
    }

    @Test
    void shouldReturnBrandAsDto() throws ElementNotFoundException {
        Mockito.when(brandSearch.getByCode(BRAND_CODE)).thenReturn(brand);
        BrandDto result = brandController.getByCode(BRAND_CODE);

        Assertions.assertEquals(brandDto, result);
    }

    @Test
    void shouldReturnBrandListAsDomain() {
        Mockito.when(brandSearch.getAll()).thenReturn(brandList);
        List<BrandDto> result = brandController.getAll();

        Assertions.assertArrayEquals(brandDtoList.toArray(), result.toArray());
    }

}

package br.com.correa.wardrobemanager.infra.controller.brand;

import br.com.correa.wardrobemanager.application.exceptions.ElementCodeConflictException;
import br.com.correa.wardrobemanager.application.exceptions.ElementNotFoundException;
import br.com.correa.wardrobemanager.application.exceptions.InvalidElementException;
import br.com.correa.wardrobemanager.application.usecases.brand.BrandCreation;
import br.com.correa.wardrobemanager.application.usecases.brand.BrandDeletion;
import br.com.correa.wardrobemanager.application.usecases.brand.BrandEdition;
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
    @Mock
    private BrandDeletion brandDeletion;
    @Mock
    private BrandEdition brandEdition;

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

    @Test
    void shouldRedirectToDeleteUseCaseAndReturnDeletedElementIfSuccess() throws ElementNotFoundException {
        Mockito.when(brandDeletion.delete(BRAND_CODE)).thenReturn(brand);
        BrandDto result = brandController.delete(BRAND_CODE);

        Assertions.assertEquals(brandDto, result);
        Mockito.verify(brandDeletion).delete(BRAND_CODE);
    }

    @Test
    void shouldRedirectToDeleteUseCaseAndThrowException() throws ElementNotFoundException {
        Mockito.when(brandDeletion.delete(BRAND_CODE)).thenThrow(ElementNotFoundException.class);
        Assertions.assertThrows(ElementNotFoundException.class, () -> brandController.delete(BRAND_CODE));

        Mockito.verify(brandDeletion).delete(BRAND_CODE);
    }

    @Test
    void shouldReturnEditedBrandAsDto() throws ElementNotFoundException {
        Mockito.when(brandEdition.edit(BRAND_CODE, brand)).thenReturn(brand);
        BrandDto result = brandController.edit(BRAND_CODE, brandDto);

        Assertions.assertEquals(brandDto, result);
    }

    @Test
    void shouldRedirectToEditUseCaseAndThrowExceptionIfNotFound() throws ElementNotFoundException {
        Mockito.when(brandEdition.edit(BRAND_CODE,brand)).thenThrow(ElementNotFoundException.class);
        Assertions.assertThrows(ElementNotFoundException.class, () -> brandController.edit(BRAND_CODE, brandDto));

        Mockito.verify(brandEdition).edit(BRAND_CODE, brand);
    }

    @Test
    void shouldRefuseNullSource() {
        Assertions.assertThrows(InvalidElementException.class, () -> brandController.create(null));
    }
}

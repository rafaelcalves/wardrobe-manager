package br.com.correa.wardrobemanager.infra.controller.piece;

import br.com.correa.wardrobemanager.application.exceptions.ElementCodeConflictException;
import br.com.correa.wardrobemanager.application.exceptions.ElementNotFoundException;
import br.com.correa.wardrobemanager.application.exceptions.InvalidElementException;
import br.com.correa.wardrobemanager.application.usecases.brand.BrandSearch;
import br.com.correa.wardrobemanager.application.usecases.category.CategorySearch;
import br.com.correa.wardrobemanager.application.usecases.piece.PieceCreation;
import br.com.correa.wardrobemanager.application.usecases.piece.PieceDeletion;
import br.com.correa.wardrobemanager.application.usecases.piece.PieceSearch;
import br.com.correa.wardrobemanager.config.ObjectMapperConfig;
import br.com.correa.wardrobemanager.domain.entities.Brand;
import br.com.correa.wardrobemanager.domain.entities.Category;
import br.com.correa.wardrobemanager.domain.entities.Piece;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.hosuaby.inject.resources.junit.jupiter.GivenJsonResource;
import io.hosuaby.inject.resources.junit.jupiter.TestWithResources;
import io.hosuaby.inject.resources.junit.jupiter.WithJacksonMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
class PieceControllerTest {
    public static final String PIECE_CODE = "piece_code";

    public static final String BRAND_CODE = "brand_code";
    public static final String CATEGORY_CODE = "category_code";

    @InjectMocks
    private PieceController pieceController;

    @Mock
    private PieceCreation pieceCreation;
    @Mock
    private PieceSearch pieceSearch;
    @Mock
    private PieceDeletion pieceDeletion;

    @Mock
    private BrandSearch brandSearch;
    @Mock
    private CategorySearch categorySearch;

    @Spy
    private PieceDtoMapper pieceDtoMapper = new PieceDtoMapperImpl();

    @WithJacksonMapper
    ObjectMapper mapper = ObjectMapperConfig.getObjectMapper();
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/piece.json")
    Piece piece;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/infra/controller/piece/pieceDto.json")
    PieceDto pieceDto;

    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/simpleBrand.json")
    Brand brand;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/simpleCategory.json")
    Category category;

    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/pieceList.json")
    List<Piece> pieceList;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/infra/controller/piece/pieceDtoList.json")
    List<PieceDto> pieceDtoList;

    @BeforeEach
    void setUp() {
        pieceDtoMapper.setBrandSearch(brandSearch);
        pieceDtoMapper.setCategorySearch(categorySearch);
    }

    @Test
    void shouldRedirectToUseCaseAsDomainValue() throws ElementCodeConflictException, ElementNotFoundException {
        Mockito.when(pieceCreation.create(piece)).thenReturn(piece);
        Mockito.when(brandSearch.getByCode(BRAND_CODE)).thenReturn(brand);
        Mockito.when(categorySearch.getByCode(CATEGORY_CODE)).thenReturn(category);

        PieceDto result = pieceController.create(pieceDto);

        Assertions.assertEquals(pieceDto, result);
    }

    @Test
    void shouldReturnPieceAsDto() throws ElementNotFoundException {
        Mockito.when(pieceSearch.getByCode(PIECE_CODE)).thenReturn(piece);
        PieceDto result = pieceController.getByCode(PIECE_CODE);

        Assertions.assertEquals(pieceDto, result);
    }

    @Test
    void shouldReturnPieceListAsDomain() {
        Mockito.when(pieceSearch.getAll()).thenReturn(pieceList);
        List<PieceDto> result = pieceController.getAll();

        Assertions.assertArrayEquals(pieceDtoList.toArray(), result.toArray());
    }

    @Test
    void shouldRedirectToDeleteUseCaseAndReturnDeletedElementIfSuccess() throws ElementNotFoundException {
        Mockito.when(pieceDeletion.delete(PIECE_CODE)).thenReturn(piece);
        PieceDto result = pieceController.delete(PIECE_CODE);

        Assertions.assertEquals(pieceDto, result);
        Mockito.verify(pieceDeletion).delete(PIECE_CODE);
    }

    @Test
    void shouldRedirectToDeleteUseCaseAndThrowException() throws ElementNotFoundException {
        Mockito.when(pieceDeletion.delete(PIECE_CODE)).thenThrow(ElementNotFoundException.class);
        Assertions.assertThrows(ElementNotFoundException.class, () -> pieceController.delete(PIECE_CODE));

        Mockito.verify(pieceDeletion).delete(PIECE_CODE);
    }

    @Test
    void shouldRefuseNullSource() {
        Assertions.assertThrows(InvalidElementException.class, () -> pieceController.create(null));
    }

}

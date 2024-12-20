package br.com.correa.wardrobemanager.infra.gateways.mongo.piece;

import br.com.correa.wardrobemanager.application.exceptions.ElementNotFoundException;
import br.com.correa.wardrobemanager.application.usecases.brand.BrandSearch;
import br.com.correa.wardrobemanager.application.usecases.category.CategorySearch;
import br.com.correa.wardrobemanager.config.ObjectMapperConfig;
import br.com.correa.wardrobemanager.domain.entities.Brand;
import br.com.correa.wardrobemanager.domain.entities.Category;
import br.com.correa.wardrobemanager.domain.entities.Piece;
import br.com.correa.wardrobemanager.infra.persistence.mongo.piece.PieceDocument;
import br.com.correa.wardrobemanager.infra.persistence.mongo.piece.PieceRepository;
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
import java.util.Optional;

@TestWithResources
@ExtendWith(MockitoExtension.class)
class PieceMongoGatewayTest {
    public static final String PIECE_CODE = "piece_code";
    public static final String BRAND_CODE = "brand_code";
    public static final String CATEGORY_CODE = "category_code";
    @InjectMocks
    private PieceMongoGateway pieceMongoGateway;

    @Mock
    private PieceRepository pieceRepository;
    @Mock
    private BrandSearch brandSearch;
    @Mock
    private CategorySearch categorySearch;
    @Spy
    private PieceDocumentMapper pieceDocumentMapper = new PieceDocumentMapperImpl();

    @WithJacksonMapper
    ObjectMapper mapper = ObjectMapperConfig.getObjectMapper();
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/piece.json")
    Piece piece;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/persistence/mongo/piece/noIdPieceDocument.json")
    PieceDocument pieceDocumentInput;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/simpleBrand.json")
    Brand brand;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/simpleCategory.json")
    Category category;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/persistence/mongo/piece/pieceDocument.json")
    PieceDocument pieceDocumentOutput;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/persistence/mongo/piece/pieceDocumentList.json")
    List<PieceDocument> pieceDocumentList;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/pieceList.json")
    List<Piece> pieceList;

    @BeforeEach
    void setUp() {
        pieceDocumentMapper.setBrandSearch(brandSearch);
        pieceDocumentMapper.setCategorySearch(categorySearch);
    }

    @Test
    void shouldRedirectEntityAsDocumentToRepository() throws ElementNotFoundException {
        Mockito.when(pieceRepository.save(pieceDocumentInput)).thenReturn(pieceDocumentOutput);
        Mockito.when(brandSearch.getByCode(BRAND_CODE)).thenReturn(brand);
        Mockito.when(categorySearch.getByCode(CATEGORY_CODE)).thenReturn(category);

        Piece result = pieceMongoGateway.createPiece(piece);
        Assertions.assertEquals(piece, result);
        Mockito.verify(pieceRepository).save(pieceDocumentInput);
    }

    @Test
    void shouldReturnPieceAsDomain() throws ElementNotFoundException {
        Mockito.when(brandSearch.getByCode(BRAND_CODE)).thenReturn(brand);
        Mockito.when(categorySearch.getByCode(CATEGORY_CODE)).thenReturn(category);
        Mockito.when(pieceRepository.findByCode(PIECE_CODE)).thenReturn(Optional.of(pieceDocumentOutput));
        Optional<Piece> result = pieceMongoGateway.getPieceByCode(PIECE_CODE);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(piece, result.get());
    }

    @Test
    void shouldReturnPieceListAsDomain() throws ElementNotFoundException {
        Mockito.when(brandSearch.getByCode(BRAND_CODE)).thenReturn(brand);
        Mockito.when(categorySearch.getByCode(CATEGORY_CODE)).thenReturn(category);
        Mockito.when(pieceRepository.findAll()).thenReturn(pieceDocumentList);
        List<Piece> result = pieceMongoGateway.getAllPieces();

        Assertions.assertArrayEquals(pieceList.toArray(), result.toArray());
    }

    @Test
    void shouldReturnEmptyListIfNullOnRepository() {
        Mockito.when(pieceRepository.findAll()).thenReturn(null);
        List<Piece> result = pieceMongoGateway.getAllPieces();

        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnDeletedPiece() throws ElementNotFoundException {
        Mockito.when(pieceRepository.deleteByCode(PIECE_CODE)).thenReturn(Optional.of(pieceDocumentOutput));
        Mockito.when(brandSearch.getByCode(BRAND_CODE)).thenReturn(brand);
        Mockito.when(categorySearch.getByCode(CATEGORY_CODE)).thenReturn(category);
        Optional<Piece> result = pieceMongoGateway.deletePiece(PIECE_CODE);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(piece, result.get());
    }

    @Test
    void deleteShouldReturnOptionalEmptyIfDoesntExists() {
        Mockito.when(pieceRepository.deleteByCode(PIECE_CODE)).thenReturn(Optional.empty());
        Optional<Piece> result = pieceMongoGateway.deletePiece(PIECE_CODE);

        Assertions.assertFalse(result.isPresent());
    }
}

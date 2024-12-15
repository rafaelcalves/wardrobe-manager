package br.com.correa.wardrobemanager.application.usecases.piece;

import br.com.correa.wardrobemanager.application.exceptions.ElementNotFoundException;
import br.com.correa.wardrobemanager.application.gateways.PieceDSGateway;
import br.com.correa.wardrobemanager.config.ObjectMapperConfig;
import br.com.correa.wardrobemanager.domain.entities.Piece;
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

import java.util.List;
import java.util.Optional;

@TestWithResources
@ExtendWith(MockitoExtension.class)
class PieceSearchTest {
    public static final String PIECE_CODE = "piece_code";
    @InjectMocks
    private PieceSearch pieceSearch;

    @Mock
    private PieceDSGateway pieceDSGateway;

    @WithJacksonMapper
    ObjectMapper mapper = ObjectMapperConfig.getObjectMapper();
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/piece.json")
    Piece piece;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/pieceList.json")
    List<Piece> pieceList;

    @Test
    void shouldReturnPieceMatchingTheCode() throws ElementNotFoundException {
        Mockito.when(pieceDSGateway.getPieceByCode(PIECE_CODE)).thenReturn(Optional.of(piece));

        Piece result = pieceSearch.getByCode(PIECE_CODE);

        Assertions.assertEquals(piece, result);
    }

    @Test
    void shouldReturnExceptionIfPieceNotFound() {
        Mockito.when(pieceDSGateway.getPieceByCode(PIECE_CODE)).thenReturn(Optional.empty());

        Assertions.assertThrows(ElementNotFoundException.class, () -> pieceSearch.getByCode(PIECE_CODE));
    }

    @Test
    void shouldReturnAllPieces() {
        Mockito.when(pieceDSGateway.getAllPieces()).thenReturn(pieceList);

        List<Piece> result = pieceSearch.getAll();
        Assertions.assertArrayEquals(pieceList.toArray(), result.toArray());
    }
}

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

import java.util.Optional;

@TestWithResources
@ExtendWith(MockitoExtension.class)
class PieceEditionTest {
    public static final String PIECE_CODE = "piece_code";
    @InjectMocks
    private PieceEdition pieceEdition;

    @Mock
    private PieceDSGateway pieceDSGateway;

    @WithJacksonMapper
    ObjectMapper mapper = ObjectMapperConfig.getObjectMapper();
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/piece.json")
    Piece piece;

    @Test
    void shouldReturnPieceMatchingTheCode() throws ElementNotFoundException {
        Mockito.when(pieceDSGateway.editPiece(PIECE_CODE, piece)).thenReturn(Optional.of(piece));

        Piece result = pieceEdition.edit(PIECE_CODE, piece);

        Assertions.assertEquals(piece, result);
    }

    @Test
    void shouldReturnExceptionIfPieceNotFound() {
        Mockito.when(pieceDSGateway.editPiece(PIECE_CODE, piece)).thenReturn(Optional.empty());

        Assertions.assertThrows(ElementNotFoundException.class, () -> pieceEdition.edit(PIECE_CODE, piece));
    }
}

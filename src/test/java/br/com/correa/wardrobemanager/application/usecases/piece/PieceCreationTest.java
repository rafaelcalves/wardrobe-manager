package br.com.correa.wardrobemanager.application.usecases.piece;

import br.com.correa.wardrobemanager.application.exceptions.ElementCodeConflictException;
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

@TestWithResources
@ExtendWith(MockitoExtension.class)
class PieceCreationTest {
    @InjectMocks
    private PieceCreation pieceCreation;

    @Mock
    private PieceDSGateway pieceDSGateway;
    @Mock
    private PieceSearch pieceSearch;

    @WithJacksonMapper
    ObjectMapper mapper = ObjectMapperConfig.getObjectMapper();
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/piece.json")
    Piece piece;
    
    @Test
    void pieceCreationShouldReturnReceivedPieceAndCallGateway() throws ElementCodeConflictException, ElementNotFoundException {
        Mockito.when(pieceSearch.getByCode(Mockito.any())).thenThrow(ElementNotFoundException.class);
        Mockito.when(pieceDSGateway.createPiece(Mockito.any())).thenReturn(piece);

        var result = pieceCreation.create(piece);

        Mockito.verify(pieceDSGateway).createPiece(piece);
        Assertions.assertEquals(piece, result);
    }

    @Test
    void shouldThrowExceptionIfElementCodeExists() throws ElementNotFoundException {
        Mockito.when(pieceSearch.getByCode(Mockito.any())).thenReturn(piece);

        Assertions.assertThrows(ElementCodeConflictException.class, () -> pieceCreation.create(piece));
    }
}


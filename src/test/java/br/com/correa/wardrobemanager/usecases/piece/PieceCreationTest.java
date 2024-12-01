package br.com.correa.wardrobemanager.usecases.piece;

import br.com.correa.wardrobemanager.ObjectMapperConfig;
import br.com.correa.wardrobemanager.application.gateways.PieceDSGateway;
import br.com.correa.wardrobemanager.application.usecases.piece.PieceCreation;
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
    @Mock
    private PieceDSGateway pieceDSGateway;
    @InjectMocks
    private PieceCreation pieceCreation;

    @WithJacksonMapper
    ObjectMapper mapper = ObjectMapperConfig.getObjectMapper();
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/piece.json")
    Piece piece;

    @Test
    void pieceCreationShouldReturnReceivedPieceAndCallGateway() {
        Mockito.when(pieceDSGateway.createPiece(Mockito.any())).thenReturn(piece);

        var result = pieceCreation.create(piece);

        Mockito.verify(pieceDSGateway).createPiece(piece);
        Assertions.assertEquals(piece, result);
    }
}


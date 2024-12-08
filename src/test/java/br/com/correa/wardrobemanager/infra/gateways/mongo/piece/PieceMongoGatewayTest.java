package br.com.correa.wardrobemanager.infra.gateways.mongo.piece;

import br.com.correa.wardrobemanager.ObjectMapperConfig;
import br.com.correa.wardrobemanager.domain.entities.Piece;
import br.com.correa.wardrobemanager.infra.persistence.mongo.piece.PieceDocument;
import br.com.correa.wardrobemanager.infra.persistence.mongo.piece.PieceRepository;
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
class PieceMongoGatewayTest {
    @InjectMocks
    private PieceMongoGateway pieceMongoGateway;

    @Mock
    private PieceRepository pieceRepository;
    @Spy
    private PieceDocumentMapper pieceDocumentMapper = new PieceDocumentMapperImpl();

    @WithJacksonMapper
    ObjectMapper mapper = ObjectMapperConfig.getObjectMapper();
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/piece.json")
    Piece piece;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/piece.json")
    PieceDocument pieceDocumentInput;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/persistence/mongo/piece/pieceDto.json")
    PieceDocument pieceDocumentOutput;

    @Test
    void shouldRedirectEntityAsDocumentToRepository() {
        Mockito.when(pieceRepository.save(pieceDocumentInput)).thenReturn(pieceDocumentOutput);

        Piece result = pieceMongoGateway.createPiece(piece);
        Assertions.assertEquals(piece, result);
        Mockito.verify(pieceRepository).save(pieceDocumentInput);
    }
}

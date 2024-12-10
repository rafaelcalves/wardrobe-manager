package br.com.correa.wardrobemanager.infra.controller.piece;

import br.com.correa.wardrobemanager.application.usecases.piece.PieceCreation;
import br.com.correa.wardrobemanager.config.ObjectMapperConfig;
import br.com.correa.wardrobemanager.domain.entities.Piece;
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
class PieceControllerTest {
    @InjectMocks
    private PieceController pieceController;

    @Mock
    private PieceCreation pieceCreation;

    @Spy
    private PieceDtoMapper pieceDtoMapper = new PieceDtoMapperImpl();

    @WithJacksonMapper
    ObjectMapper mapper = ObjectMapperConfig.getObjectMapper();
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/piece.json")
    Piece piece;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/piece.json")
    PieceDto pieceDto;

    @Test
    void shouldRedirectToUseCaseAsDomainValue() {
        Mockito.when(pieceCreation.create(piece)).thenReturn(piece);

        PieceDto result = pieceController.create(pieceDto);

        Assertions.assertEquals(pieceDto, result);
    }
}

package br.com.correa.wardrobemanager.infra.controller.piece;

import br.com.correa.wardrobemanager.config.ObjectMapperConfig;
import br.com.correa.wardrobemanager.domain.entities.Piece;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.hosuaby.inject.resources.junit.jupiter.GivenJsonResource;
import io.hosuaby.inject.resources.junit.jupiter.GivenTextResource;
import io.hosuaby.inject.resources.junit.jupiter.TestWithResources;
import io.hosuaby.inject.resources.junit.jupiter.WithJacksonMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@TestWithResources
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PieceControllerIntegrationTest {

    public static final String UNREGISTERED_PIECE_CODE = "unregistered_piece_code";
    @Autowired
    private MockMvc mockMvc;
    
    @Container
    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @WithJacksonMapper
    ObjectMapper mapper = ObjectMapperConfig.getObjectMapper();
    @GivenJsonResource("json/br/com/correa/wardrobemanager/domain/entities/piece.json")
    Piece piece;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/piece/pieceDto.json")
    String pieceDtoJson;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/brand/brandDto.json")
    String brandDtoJson;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/category/categoryDto.json")
    String categoryDtoJson;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/piece/noCodePieceDto.json")
    String noCodePieceDtoJson;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/infra/controller/piece/pieceDtoList.json")
    List<PieceDto> pieceDtoList;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/piece/pieceDtoList.json")
    String pieceDtoListJson;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/piece/exception/code_exists_exception.json")
    String codeExistsAsProblemJson;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/piece/exception/not_found_exception.json")
    String notFoundAsProblemJson;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/piece/exception/code_invalid_exception.json")
    String codeInvalidAsProblemJson;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/piece/exception/name_invalid_exception.json")
    String nameInvalidAsProblemJson;

    @Test
    @Order(1)
    void shouldInsertPieceToDatabase() throws Exception {
        mockMvc.perform(post("/brand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandDtoJson))
                .andExpect(status().isOk());

        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryDtoJson))
                .andExpect(status().isOk());

        for (PieceDto pieceDto : pieceDtoList) {
            assertPieceCreation(pieceDto);
        }

        assertFindAll();
    }

    @Test
    @Order(2)
    void shouldReceiveProblemDetailAsConflictErrorResponse() throws Exception {
        mockMvc.perform(post("/piece")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pieceDtoJson))
                .andExpect(status().isConflict())
                .andExpect(content().json(codeExistsAsProblemJson));
    }

    @Test
    @Order(3)
    void shouldReceiveProblemDetailAsNotFoundErrorResponse() throws Exception {
        mockMvc.perform(get("/piece/" + UNREGISTERED_PIECE_CODE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(notFoundAsProblemJson));
    }

    @Test
    @Order(4)
    void shouldReceiveProblemDetailAsInvalidCodeErrorResponse() throws Exception {
        mockMvc.perform(post("/piece")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(noCodePieceDtoJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(codeInvalidAsProblemJson));
    }

    private void assertPieceCreation(PieceDto piece) throws Exception {
        mockMvc.perform(post("/piece")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(piece)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(piece)));


        mockMvc.perform(get("/piece/" + piece.code())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(piece)));
    }

    private void assertFindAll() throws Exception {
        mockMvc.perform(get("/piece")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(pieceDtoListJson));
    }
}

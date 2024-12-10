package br.com.correa.wardrobemanager.infra.controller.brand;

import br.com.correa.wardrobemanager.config.ObjectMapperConfig;
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
class BrandControllerIntegrationTest {

    public static final String UNREGISTERED_BRAND_CODE = "unregistered_brand_code";
    @Autowired
    private MockMvc mockMvc;
    
    @Container
    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @WithJacksonMapper
    ObjectMapper mapper = ObjectMapperConfig.getObjectMapper();
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/brand/brandDto.json")
    String brandDtoJson;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/infra/controller/brand/brandDtoList.json")
    List<BrandDto> brandDtoList;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/brand/brandDtoList.json")
    String brandDtoListJson;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/brand/exception/code_exists_exception.json")
    String codeExistsAsProblemJson;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/brand/exception/not_found_exception.json")
    String notFoundAsProblemJson;

    @Test
    @Order(1)
    void shouldInsertBrandToDatabase() throws Exception {
        for (BrandDto brand : brandDtoList) {
            assertBrandCreation(brand);
        }

        assertFindAll();
    }

    @Test
    @Order(2)
    void shouldReceiveProblemDetailAsConflictErrorResponse() throws Exception {
        mockMvc.perform(post("/brand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandDtoJson))
                .andExpect(status().isConflict())
                .andExpect(content().json(codeExistsAsProblemJson));
    }

    @Test
    @Order(3)
    void shouldReceiveProblemDetailAsNotFoundErrorResponse() throws Exception {
        mockMvc.perform(get("/brand/" + UNREGISTERED_BRAND_CODE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(notFoundAsProblemJson));
    }

    private void assertBrandCreation(BrandDto brand) throws Exception {
        mockMvc.perform(post("/brand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(brand)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(brand)));


        mockMvc.perform(get("/brand/" + brand.code())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(brand)));
    }

    private void assertFindAll() throws Exception {
        mockMvc.perform(get("/brand")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(brandDtoListJson));
    }

}

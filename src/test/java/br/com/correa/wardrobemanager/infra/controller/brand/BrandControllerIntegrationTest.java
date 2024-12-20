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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@TestWithResources
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/brand/brandDto_old.json")
    String brandDtoOldJson;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/brand/brandDto_new.json")
    String brandDtoNewJson;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/infra/controller/brand/brandDto_old.json")
    BrandDto brandDtoOld;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/brand/brandDto_shouldDelete.json")
    String brandDtoShouldDeleteJson;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/infra/controller/brand/brandDto_shouldDelete.json")
    BrandDto brandDtoShouldDelete;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/brand/noCodeBrandDto.json")
    String noCodeBrandDtoJson;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/brand/noNameBrandDto.json")
    String noNameBrandDtoJson;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/infra/controller/brand/brandDtoList.json")
    List<BrandDto> brandDtoList;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/brand/brandDtoList.json")
    String brandDtoListJson;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/brand/exception/code_exists_exception.json")
    String codeExistsAsProblemJson;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/brand/exception/not_found_exception.json")
    String notFoundAsProblemJson;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/brand/exception/not_found_exception_delete.json")
    String notFoundDeleteAsProblemJson;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/brand/exception/code_invalid_exception.json")
    String codeInvalidAsProblemJson;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/brand/exception/name_invalid_exception.json")
    String nameInvalidAsProblemJson;

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
                .andExpect(status().isOk());

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

    @Test
    @Order(4)
    void shouldReceiveProblemDetailAsInvalidCodeErrorResponse() throws Exception {
        mockMvc.perform(post("/brand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(noCodeBrandDtoJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(codeInvalidAsProblemJson));
    }

    @Test
    @Order(5)
    void shouldReceiveProblemDetailAsInvalidNameErrorResponse() throws Exception {
        mockMvc.perform(post("/brand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(noNameBrandDtoJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(nameInvalidAsProblemJson));
    }

    @Test
    @Order(6)
    void shouldSuccessfullyDeleteElement() throws Exception {
        mockMvc.perform(post("/brand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandDtoShouldDeleteJson))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/brand/" + brandDtoShouldDelete.code())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(brandDtoShouldDeleteJson));

        mockMvc.perform(delete("/brand/" + brandDtoShouldDelete.code())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(notFoundDeleteAsProblemJson));
    }

    @Test
    @Order(7)
    void shouldSuccessfullyEditElement() throws Exception {
        mockMvc.perform(post("/brand")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandDtoOldJson))
                .andExpect(status().isOk());

        mockMvc.perform(put("/brand/" + brandDtoOld.code())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(brandDtoNewJson))
                .andExpect(status().isOk())
                .andExpect(content().json(brandDtoNewJson));
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

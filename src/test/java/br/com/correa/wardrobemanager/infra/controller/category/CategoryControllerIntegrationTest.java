package br.com.correa.wardrobemanager.infra.controller.category;

import br.com.correa.wardrobemanager.config.ObjectMapperConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.hosuaby.inject.resources.junit.jupiter.GivenJsonResource;
import io.hosuaby.inject.resources.junit.jupiter.GivenTextResource;
import io.hosuaby.inject.resources.junit.jupiter.TestWithResources;
import io.hosuaby.inject.resources.junit.jupiter.WithJacksonMapper;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@TestWithResources
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerIntegrationTest {

    public static final String UNREGISTERED_CATEGORY_CODE = "unregistered_category_code";
    @Autowired
    private MockMvc mockMvc;
    
    @Container
    @ServiceConnection
    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));

    @WithJacksonMapper
    ObjectMapper mapper = ObjectMapperConfig.getObjectMapper();
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/category/categoryDto.json")
    String categoryDtoJson;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/category/categoryDto_old.json")
    String categoryDtoOldJson;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/category/categoryDto_new.json")
    String categoryDtoNewJson;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/infra/controller/category/categoryDto_old.json")
    CategoryDto categoryDtoOld;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/category/noCodeCategoryDto.json")
    String noCodeCategoryDtoJson;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/category/noCodeSubCategorycategoryDto.json")
    String noCodeSubCategorycategoryDtoJson;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/category/noNameCategoryDto.json")
    String noNameCategoryDtoJson;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/infra/controller/category/categoryDtoList.json")
    List<CategoryDto> categoryDtoList;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/category/categoryDtoList.json")
    String categoryDtoListJson;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/category/exception/code_exists_exception.json")
    String codeExistsAsProblemJson;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/category/exception/not_found_exception.json")
    String notFoundAsProblemJson;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/category/exception/code_invalid_exception.json")
    String codeInvalidAsProblemJson;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/category/exception/name_invalid_exception.json")
    String nameInvalidAsProblemJson;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/category/exception/not_found_exception_delete.json")
    String notFoundDeleteAsProblemJson;
    @GivenTextResource("json/br/com/correa/wardrobemanager/infra/controller/category/categoryDto_shouldDelete.json")
    String categoryDtoShouldDeleteJson;
    @GivenJsonResource("json/br/com/correa/wardrobemanager/infra/controller/category/categoryDto_shouldDelete.json")
    CategoryDto categoryDtoShouldDelete;

    @Test
    @Order(1)
    void shouldInsertCategoriesToDatabase() throws Exception {
        for (CategoryDto category : categoryDtoList) {
            assertCategoryCreation(category);
        }

        assertFindAll();
    }

    @Test
    @Order(2)
    void shouldReceiveProblemDetailAsConflictErrorResponse() throws Exception {
        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryDtoJson))
                .andExpect(status().isConflict())
                .andExpect(content().json(codeExistsAsProblemJson));
    }

    @Test
    @Order(3)
    void shouldReceiveProblemDetailAsNotFoundErrorResponse() throws Exception {
        mockMvc.perform(get("/category/" + UNREGISTERED_CATEGORY_CODE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(notFoundAsProblemJson));
    }

    @Test
    @Order(4)
    void shouldReceiveProblemDetailAsInvalidCodeErrorResponse() throws Exception {
        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(noCodeCategoryDtoJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(codeInvalidAsProblemJson));
    }

    @Test
    @Order(5)
    void shouldReceiveProblemDetailAsInvalidNameErrorResponse() throws Exception {
        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(noNameCategoryDtoJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(nameInvalidAsProblemJson));
    }

    @Test
    @Order(6)
    void shouldReceiveProblemDetailAsInvalidSubCategoryCodeErrorResponse() throws Exception {
        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(noCodeSubCategorycategoryDtoJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(codeInvalidAsProblemJson));
    }

    @Test
    @Order(7)
    void shouldSuccessfullyDeleteElement() throws Exception {
        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryDtoShouldDeleteJson))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/category/" + categoryDtoShouldDelete.code())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(categoryDtoShouldDeleteJson));

        mockMvc.perform(delete("/category/" + categoryDtoShouldDelete.code())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(notFoundDeleteAsProblemJson));
    }
    
    @Test
    @Order(8)
    void shouldSuccessfullyEditElement() throws Exception {
        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryDtoOldJson))
                .andExpect(status().isOk());

        mockMvc.perform(put("/category/" + categoryDtoOld.code())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(categoryDtoNewJson))
                .andExpect(status().isOk())
                .andExpect(content().json(categoryDtoNewJson));
    }

    private void assertCategoryCreation(CategoryDto category) throws Exception {
        mockMvc.perform(post("/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(category)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(category)));


        mockMvc.perform(get("/category/" + category.code())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(category)));
    }

    private void assertFindAll() throws Exception {
        mockMvc.perform(get("/category")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(categoryDtoListJson));
    }
}

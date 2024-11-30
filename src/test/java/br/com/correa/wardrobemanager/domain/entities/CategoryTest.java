package br.com.correa.wardrobemanager.domain.entities;

import io.hosuaby.inject.resources.junit.jupiter.GivenTextResource;
import io.hosuaby.inject.resources.junit.jupiter.TestWithResources;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

@TestWithResources
class CategoryTest {

    private Category category;

    @GivenTextResource("json/br/com/correa/wardrobemanager/domain/entities/category.json")
    String categoryJson;

    @BeforeEach
    void setUp() {
        category = DummyEntityFactory.buildDummyCategory();
    }

    @Test
    void toStringShouldReturnJsonFormatWithAllAttributes() throws JSONException {
        JSONAssert.assertEquals(categoryJson,category.toString(),false);
    }

}

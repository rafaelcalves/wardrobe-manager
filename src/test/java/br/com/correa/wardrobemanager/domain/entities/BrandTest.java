package br.com.correa.wardrobemanager.domain.entities;

import io.hosuaby.inject.resources.junit.jupiter.GivenTextResource;
import io.hosuaby.inject.resources.junit.jupiter.TestWithResources;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

@TestWithResources
class BrandTest {
    private Brand brand;

    @GivenTextResource("json/br/com/correa/wardrobemanager/domain/entities/brand.json")
    String brandJson;

    @BeforeEach
    void setUp() {
        brand = DummyEntityFactory.buildDummyBrand();
    }

    @Test
    void toStringShouldReturnJsonFormatWithAllAttributes() throws JSONException {
        JSONAssert.assertEquals(brandJson,brand.toString(),false);
    }
}

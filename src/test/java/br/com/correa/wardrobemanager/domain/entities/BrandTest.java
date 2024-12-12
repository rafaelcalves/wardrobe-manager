package br.com.correa.wardrobemanager.domain.entities;

import br.com.correa.wardrobemanager.domain.exceptions.InvalidEntityAttributeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.URI;
import java.util.stream.Stream;

public class BrandTest {

    public static final String BRAND_NAME = "brand_name";
    public static final String BRAND_CODE = "brand_code";
    public static final String BRAND_URL = "http://www.website.com";

    @Test
    void shouldBuildElementAsExpected() {
        Brand brand = Brand.builder().name(BRAND_NAME)
                .code(BRAND_CODE)
                .webSite(URI.create(BRAND_URL))
                .build();
        Assertions.assertEquals(BRAND_NAME, brand.getName());
        Assertions.assertEquals(BRAND_CODE, brand.getCode());
        Assertions.assertEquals(URI.create(BRAND_URL), brand.getWebSite());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("getValidationArguments")
    void shouldThrowExceptionWhenInvalidAttribute(String description, String code, String name, String url) {
        Assertions.assertThrows(InvalidEntityAttributeException.class, () -> Brand.builder()
                .code(code)
                .name(name)
                .webSite(URI.create(url))
                .build());
    }

    private static Stream<Arguments> getValidationArguments() {
        return Stream.of(
                Arguments.of("Code null",null,BRAND_NAME,BRAND_URL),
                Arguments.of("Code empty","",BRAND_NAME,BRAND_URL),
                Arguments.of("Name null",BRAND_CODE,null,BRAND_URL),
                Arguments.of("Name empty",BRAND_CODE,"",BRAND_URL)
        );
    }
}

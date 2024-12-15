package br.com.correa.wardrobemanager.domain.entities;

import br.com.correa.wardrobemanager.domain.exceptions.InvalidEntityAttributeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CategoryTest {

    public static final String CATEGORY_NAME = "category_name";
    public static final String CATEGORY_CODE = "category_code";
    public static final ArrayList<Category> SUBCATEGORIES_EMPTY_LIST = new ArrayList<>();

    @Test
    void shouldBuildElementAsExpected() {
        Category category = Category.builder().name(CATEGORY_NAME)
                .code(CATEGORY_CODE)
                .subCategories(SUBCATEGORIES_EMPTY_LIST)
                .build();
        Assertions.assertEquals(CATEGORY_NAME, category.getName());
        Assertions.assertEquals(CATEGORY_CODE, category.getCode());
        Assertions.assertEquals(SUBCATEGORIES_EMPTY_LIST, category.getSubCategories());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("getValidationArguments")
    void shouldThrowExceptionWhenInvalidAttribute(String description, String code, String name, List<Category> subCategories) {
        Assertions.assertThrows(InvalidEntityAttributeException.class, () -> Category.builder()
                .code(code)
                .name(name)
                .subCategories(subCategories)
                .build());
    }

    private static Stream<Arguments> getValidationArguments() {
        return Stream.of(
                Arguments.of("Code null",null,CATEGORY_NAME, SUBCATEGORIES_EMPTY_LIST),
                Arguments.of("Code empty","",CATEGORY_NAME, SUBCATEGORIES_EMPTY_LIST),
                Arguments.of("Name null",CATEGORY_CODE,null, SUBCATEGORIES_EMPTY_LIST),
                Arguments.of("Name empty",CATEGORY_CODE,"", SUBCATEGORIES_EMPTY_LIST)
        );
    }
}

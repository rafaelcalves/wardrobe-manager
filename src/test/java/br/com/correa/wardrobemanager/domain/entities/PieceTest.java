package br.com.correa.wardrobemanager.domain.entities;

import br.com.correa.wardrobemanager.domain.exceptions.InvalidEntityAttributeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class PieceTest {

    public static final String PIECE_CODE = "piece_code";
    public static final String PIECE_DESCRIPTION = "piece_description";
    public static final String PIECE_COLOR_HEX = "piece_color_hex";
    public static final String PIECE_FABRIC = "piece_fabric";
    public static final List<URI> PIECE_IMAGES = List.of(URI.create("image_url"));
    public static final List<URI> PIECE_LINKS = List.of(URI.create("link_url"));

    public static final String CATEGORY_CODE = "category_code";
    public static final String CATEGORY_NAME = "category_name";
    public static final Category PIECE_CATEGORY = Category.builder()
            .code(CATEGORY_CODE)
            .name(CATEGORY_NAME)
            .build();

    public static final String BRAND_CODE = "brand_code";
    public static final String BRAND_NAME = "brand_name";
    public static final Brand PIECE_BRAND = Brand.builder()
            .code(BRAND_CODE)
            .name(BRAND_NAME).build();

    @Test
    void shouldBuildElementAsExpected() {
        Piece piece = Piece.builder()
                .code(PIECE_CODE)
                .description(PIECE_DESCRIPTION)
                .predominantColorHex(PIECE_COLOR_HEX)
                .fabric(PIECE_FABRIC)
                .category(PIECE_CATEGORY)
                .brand(PIECE_BRAND)
                .images(PIECE_IMAGES)
                .links(PIECE_LINKS)
                .build();

        Assertions.assertEquals(PIECE_CODE, piece.getCode());
        Assertions.assertEquals(PIECE_DESCRIPTION, piece.getDescription());
        Assertions.assertEquals(PIECE_COLOR_HEX, piece.getPredominantColorHex());
        Assertions.assertEquals(PIECE_FABRIC, piece.getFabric());
        Assertions.assertEquals(PIECE_CATEGORY, piece.getCategory());
        Assertions.assertEquals(PIECE_BRAND, piece.getBrand());
        Assertions.assertEquals(PIECE_IMAGES, piece.getImages());
        Assertions.assertEquals(PIECE_LINKS, piece.getLinks());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("getValidationArguments")
    void shouldThrowExceptionWhenInvalidAttribute(String testDescription, String code, String description,
                                                  String colorHex, String fabric, Category category, Brand brand,
                                                  List<URI> images, List<URI> links) {
        Assertions.assertThrows(InvalidEntityAttributeException.class, () -> Piece.builder()
                .code(code)
                .description(description)
                .predominantColorHex(colorHex)
                .fabric(fabric)
                .category(category)
                .brand(brand)
                .images(images)
                .links(links)
                .build());
    }

    private static Stream<Arguments> getValidationArguments() {
        List<URI> nullValueList = new ArrayList<>();
        nullValueList.add(null);

        return Stream.of(
                Arguments.of("Code null",null,PIECE_DESCRIPTION,PIECE_COLOR_HEX,PIECE_FABRIC,PIECE_CATEGORY,PIECE_BRAND,PIECE_IMAGES,PIECE_LINKS),
                Arguments.of("Code empty","",PIECE_DESCRIPTION,PIECE_COLOR_HEX,PIECE_FABRIC,PIECE_CATEGORY,PIECE_BRAND,PIECE_IMAGES,PIECE_LINKS),
                Arguments.of("Description null",PIECE_CODE,null,PIECE_COLOR_HEX,PIECE_FABRIC,PIECE_CATEGORY,PIECE_BRAND,PIECE_IMAGES,PIECE_LINKS),
                Arguments.of("Description empty",PIECE_CODE,"",PIECE_COLOR_HEX,PIECE_FABRIC,PIECE_CATEGORY,PIECE_BRAND,PIECE_IMAGES,PIECE_LINKS),
                Arguments.of("Color Hex null",PIECE_CODE,PIECE_DESCRIPTION,null,PIECE_FABRIC,PIECE_CATEGORY,PIECE_BRAND,PIECE_IMAGES,PIECE_LINKS),
                Arguments.of("Color Hex empty",PIECE_CODE,PIECE_DESCRIPTION,"",PIECE_FABRIC,PIECE_CATEGORY,PIECE_BRAND,PIECE_IMAGES,PIECE_LINKS),
                Arguments.of("Category null",PIECE_CODE,PIECE_DESCRIPTION,PIECE_COLOR_HEX,PIECE_FABRIC,null,PIECE_BRAND,PIECE_IMAGES,PIECE_LINKS),
                Arguments.of("Images null",PIECE_CODE,PIECE_DESCRIPTION,PIECE_COLOR_HEX,PIECE_FABRIC,PIECE_CATEGORY,PIECE_BRAND,null,PIECE_LINKS),
                Arguments.of("Images with null value",PIECE_CODE,PIECE_DESCRIPTION,PIECE_COLOR_HEX,PIECE_FABRIC,PIECE_CATEGORY,PIECE_BRAND, nullValueList,PIECE_LINKS),
                Arguments.of("Links with null value",PIECE_CODE,PIECE_DESCRIPTION,PIECE_COLOR_HEX,PIECE_FABRIC,PIECE_CATEGORY,PIECE_BRAND,PIECE_IMAGES, nullValueList)
        );
    }
}

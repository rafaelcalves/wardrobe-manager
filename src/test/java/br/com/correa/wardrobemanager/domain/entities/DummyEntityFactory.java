package br.com.correa.wardrobemanager.domain.entities;

import java.net.URI;
import java.util.List;

public final class DummyEntityFactory {

    public static final String PIECE_CODE = "piece_code";
    public static final String PIECE_DESCRIPTION = "piece_description";
    public static final String PIECE_COLOR_HEX = "#FFFFFF";
    public static final String PIECE_FABRIC = "piece_fabric";
    public static final String PIECE_IMAGE_URL = "https://www.image.com";
    public static final String PIECE_LINK_URL = "https://www.link.com";

    public static final String SUB_CATEGORY_NAME = "subCategory_name";
    public static final String SUB_CATEGORY_VALUE = "subCategory_value";
    public static final String CATEGORY_NAME = "category_name";
    public static final String CATEGORY_VALUE = "category_value";

    public static final String BRAND_CODE = "brand_code";
    public static final String BRAND_NAME = "brand_name";
    public static final String BRAND_URI = "https://www.brand.com";

    public static Piece buildDummyPiece() {
        return Piece.builder()
                .code(PIECE_CODE)
                .description(PIECE_DESCRIPTION)
                .brand(DummyEntityFactory.buildDummyBrand())
                .predominantColorHex(PIECE_COLOR_HEX)
                .category(DummyEntityFactory.buildDummyCategory())
                .fabric(PIECE_FABRIC)
                .images(List.of(URI.create(PIECE_IMAGE_URL)))
                .links(List.of(URI.create(PIECE_LINK_URL)))
                .build();
    }

    public static Category buildDummyCategory() {
        Category subCategory = Category.builder()
                .name(SUB_CATEGORY_NAME)
                .value(SUB_CATEGORY_VALUE)
                .build();

        return Category.builder()
                .name(CATEGORY_NAME)
                .value(CATEGORY_VALUE)
                .subCategories(List.of(subCategory))
                .build();
    }

    public static Brand buildDummyBrand() {
        return Brand.builder()
                .code(BRAND_CODE)
                .name(BRAND_NAME)
                .webSite(URI.create(BRAND_URI))
                .build();
    }
}

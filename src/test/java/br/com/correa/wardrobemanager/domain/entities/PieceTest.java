package br.com.correa.wardrobemanager.domain.entities;

import io.hosuaby.inject.resources.junit.jupiter.GivenTextResource;
import io.hosuaby.inject.resources.junit.jupiter.TestWithResources;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.net.URI;
import java.util.List;

@TestWithResources
public class PieceTest {
    public static final String PIECE_DESCRIPTION = "piece_description";
    public static final String PIECE_COLOR_HEX = "#FFFFFF";
    public static final String PIECE_FABRIC = "piece_fabric";
    public static final String PIECE_IMAGE_URL = "https://www.image.com";
    public static final String PIECE_LINK_URL = "https://www.link.com";

    private Piece piece;

    @GivenTextResource("json/br/com/correa/wardrobemanager/domain/entities/piece.json")
    String pieceJson;

    @BeforeEach
    void setUp() {
        piece = Piece.builder()
                .description(PIECE_DESCRIPTION)
                .brand(DummyEntityFactory.buildDummyBrand())
                .predominantColorHex(PIECE_COLOR_HEX)
                .category(DummyEntityFactory.buildDummyCategory())
                .fabric(PIECE_FABRIC)
                .images(List.of(URI.create(PIECE_IMAGE_URL)))
                .links(List.of(URI.create(PIECE_LINK_URL)))
                .build();
    }

    @Test
    void toStringShouldReturnJsonFormatWithAllAttributes() throws JSONException {
        JSONAssert.assertEquals(pieceJson,piece.toString(),false);
    }
}

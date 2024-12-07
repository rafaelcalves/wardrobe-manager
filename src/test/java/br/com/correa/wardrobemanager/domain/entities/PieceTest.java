package br.com.correa.wardrobemanager.domain.entities;

import io.hosuaby.inject.resources.junit.jupiter.GivenTextResource;
import io.hosuaby.inject.resources.junit.jupiter.TestWithResources;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

@TestWithResources
class PieceTest {
    private Piece piece;

    @GivenTextResource("json/br/com/correa/wardrobemanager/domain/entities/piece.json")
    String pieceJson;

    @BeforeEach
    void setUp() {
        piece = DummyEntityFactory.buildDummyPiece();
    }


    @Test
    void toStringShouldReturnJsonFormatWithAllAttributes() throws JSONException {
        JSONAssert.assertEquals(pieceJson,piece.toString(),false);
    }
}

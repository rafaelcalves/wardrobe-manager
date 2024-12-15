package br.com.correa.wardrobemanager.domain.entities;

import br.com.correa.wardrobemanager.domain.exceptions.InvalidEntityAttributeException;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.apache.commons.lang3.Validate;

import java.net.URI;
import java.util.List;

@Getter
@Builder
@ToString
@Jacksonized
@EqualsAndHashCode
public class Piece {
    private String code;
    private String description;
    private Brand brand;
    private String predominantColorHex;
    private Category category;
    private String fabric;
    private List<URI> images;
    private List<URI> links;

    public static Piece.PieceBuilder builder() {
        return new Piece.CustomPieceBuilder();
    }

    private static class CustomPieceBuilder extends Piece.PieceBuilder {

        @Override
        public Piece build() {
            try {
                Validate.notBlank(super.code, "Piece code cannot be blank");
                Validate.notBlank(super.description, "Piece description cannot be blank");
                Validate.notBlank(super.predominantColorHex, "Piece predominant color hex cannot be blank");
                Validate.notNull(super.category, "Piece category cannot be null");
                Validate.notEmpty(super.images, "Piece should have at least one image");
                Validate.noNullElements(super.images, "Piece image cannot be null. Index %d is null");
                Validate.noNullElements(super.links, "Piece link cannot be null. Index %d is null");
            } catch (NullPointerException|IllegalArgumentException e) {
                throw new InvalidEntityAttributeException(e.getMessage());
            }

            return super.build();
        }
    }
}

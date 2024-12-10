package br.com.correa.wardrobemanager.domain.entities;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.net.URI;
import java.util.List;

@Getter
@Builder
@ToString
@Jacksonized
@EqualsAndHashCode
@AllArgsConstructor
public class Piece {
    private String code;
    private String description;
    private Brand brand;
    private String predominantColorHex;
    private Category category;
    private String fabric;
    private List<URI> images;
    private List<URI> links;
}

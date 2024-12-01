package br.com.correa.wardrobemanager.infra.controller.piece;

import java.net.URI;
import java.util.List;

public record PieceDto(
        String code,
        String description,
        String brandCode,
        String predominantColorHex,
        String categoryCode,
        String fabric,
        List<URI>images,
        List<URI> links) {
}

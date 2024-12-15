package br.com.correa.wardrobemanager.infra.persistence.mongo.piece;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.net.URI;
import java.util.List;

@Data
@Builder
@Jacksonized
@Document("pieces")
public class PieceDocument {
    @Id
    private String id;
    private String code;
    private String description;
    private String brandCode;
    private String predominantColorHex;
    private String categoryCode;
    private String fabric;
    private List<URI> images;
    private List<URI> links;
}

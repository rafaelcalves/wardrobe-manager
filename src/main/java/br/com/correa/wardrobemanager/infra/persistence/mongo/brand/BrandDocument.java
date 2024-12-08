package br.com.correa.wardrobemanager.infra.persistence.mongo.brand;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.net.URI;

@Data
@Builder
@Jacksonized
@Document("brands")
public class BrandDocument {
    @Id
    private String id;
    private String code;
    private String name;
    private URI webSite;
}

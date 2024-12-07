package br.com.correa.wardrobemanager.infra.persistence.mongo.category;

import br.com.correa.wardrobemanager.domain.entities.Category;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document("categories")
public class CategoryDocument {
    @Id
    private String id;
    private String name;
    private String value;
    private List<Category> subCategories;
}

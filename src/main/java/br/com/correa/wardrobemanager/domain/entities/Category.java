package br.com.correa.wardrobemanager.domain.entities;

import br.com.correa.wardrobemanager.domain.exceptions.InvalidEntityAttributeException;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.apache.commons.lang3.Validate;

import java.util.List;

@Getter
@Builder
@ToString
@Jacksonized
@EqualsAndHashCode
public class Category {
    private String code;
    private String name;
    private List<Category> subCategories;

    public static CategoryBuilder builder() {
        return new CustomCategoryBuilder();
    }

    private static class CustomCategoryBuilder extends CategoryBuilder {
        @Override
        public Category build() {
            try {
                Validate.notBlank(super.code, "Category code cannot be blank");
                Validate.notBlank(super.name, "Category name cannot be blank");
            } catch (NullPointerException|IllegalArgumentException e) {
                throw new InvalidEntityAttributeException(e.getMessage());
            }

            return super.build();
        }
    }
}

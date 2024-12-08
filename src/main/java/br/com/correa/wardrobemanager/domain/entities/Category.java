package br.com.correa.wardrobemanager.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.Arrays;
import java.util.List;

@Getter
@Builder
@Jacksonized
@AllArgsConstructor
public class Category {
    private String name;
    private String code;
    private List<Category> subCategories;

    @Override
    public String toString() {
        var subCategoriesString = subCategories != null ? Arrays.deepToString(this.subCategories.toArray()) : null;
        return """
                {
                    "name": "%s",
                    "code": "%s",
                    "subCategories": %s,
                    "class": "%s"
                }""".formatted(this.name, this.code, subCategoriesString, this.getClass().getName());
    }
}

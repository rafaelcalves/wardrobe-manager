package br.com.correa.wardrobemanager.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Builder
@Jacksonized
@AllArgsConstructor
public class Category {
    private String name;
    @Getter
    private String value;
    private List<Category> subCategories;

    @Override
    public String toString() {
        return "{" +
                "\"name\":\"" + name + "\"," +
                "\"value\":\"" + value + "\"," +
                "\"subCategories\":" + subCategories + "," +
                "\"class\":\"" + this.getClass().getName() + "\"" +
                "}";
    }
}

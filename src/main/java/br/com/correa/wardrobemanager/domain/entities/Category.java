package br.com.correa.wardrobemanager.domain.entities;

import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Getter
@Builder
@ToString
@Jacksonized
@EqualsAndHashCode
@AllArgsConstructor
public class Category {
    private String name;
    private String code;
    private List<Category> subCategories;
}

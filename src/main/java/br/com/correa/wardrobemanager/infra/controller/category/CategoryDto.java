package br.com.correa.wardrobemanager.infra.controller.category;

import br.com.correa.wardrobemanager.domain.entities.Category;

import java.util.List;

public record CategoryDto(
        String name,
        String code,
        List<Category>subCategories) {
}

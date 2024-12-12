package br.com.correa.wardrobemanager.infra.controller.category;

import java.util.List;

public record CategoryDto(
        String name,
        String code,
        List<CategoryDto>subCategories) {
}

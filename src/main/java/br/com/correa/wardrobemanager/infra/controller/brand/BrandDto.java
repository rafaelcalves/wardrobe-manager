package br.com.correa.wardrobemanager.infra.controller.brand;

import java.net.URI;

public record BrandDto(
        String code,
        String name,
        URI webSite) {
}

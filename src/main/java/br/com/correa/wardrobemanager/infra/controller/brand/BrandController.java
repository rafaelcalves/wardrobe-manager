package br.com.correa.wardrobemanager.infra.controller.brand;

import br.com.correa.wardrobemanager.application.usecases.brand.BrandCreation;
import br.com.correa.wardrobemanager.domain.entities.Brand;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandController {
    private final BrandCreation brandCreation;
    private final BrandDtoMapper brandDtoMapper;

    @PostMapping
    public BrandDto create(@RequestBody BrandDto brandDto) {
        Brand domain = brandDtoMapper.toDomain(brandDto);
        return brandDtoMapper.toDto(brandCreation.create(domain));
    }
}

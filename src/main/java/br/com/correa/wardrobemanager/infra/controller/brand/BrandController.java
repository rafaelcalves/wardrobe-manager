package br.com.correa.wardrobemanager.infra.controller.brand;

import br.com.correa.wardrobemanager.application.exceptions.ElementCodeConflictException;
import br.com.correa.wardrobemanager.application.exceptions.ElementNotFoundException;
import br.com.correa.wardrobemanager.application.usecases.brand.BrandCreation;
import br.com.correa.wardrobemanager.application.usecases.brand.BrandSearch;
import br.com.correa.wardrobemanager.domain.entities.Brand;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandController {
    private final BrandCreation brandCreation;
    private final BrandSearch brandSearch;
    private final BrandDtoMapper brandDtoMapper;

    @PostMapping
    public BrandDto create(@RequestBody BrandDto brandDto) throws ElementCodeConflictException {
        Brand domain = brandDtoMapper.toDomain(brandDto);
        return brandDtoMapper.toDto(brandCreation.create(domain));
    }

    @GetMapping("/{code}")
    public BrandDto getByCode(@PathVariable String code) throws ElementNotFoundException {
        return brandDtoMapper.toDto(brandSearch.getByCode(code));
    }

    @GetMapping
    public List<BrandDto> getAll() {
        return brandDtoMapper.toDto(brandSearch.getAll());
    }
}

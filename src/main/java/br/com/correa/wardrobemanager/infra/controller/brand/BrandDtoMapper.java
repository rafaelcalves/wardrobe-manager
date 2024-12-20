package br.com.correa.wardrobemanager.infra.controller.brand;

import br.com.correa.wardrobemanager.domain.entities.Brand;
import br.com.correa.wardrobemanager.config.MapStructConfig;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapStructConfig.class)
public interface BrandDtoMapper {
    BrandDto toDto(Brand brand);
    Brand toDomain(BrandDto brandDto);

    List<BrandDto> toDto(List<Brand> brandList);
}

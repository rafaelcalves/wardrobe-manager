package br.com.correa.wardrobemanager.infra.controller.brand;

import br.com.correa.wardrobemanager.domain.entities.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BrandDtoMapper {
    BrandDto toDto(Brand brand);
    Brand toDomain(BrandDto brandDto);

    List<BrandDto> toDto(List<Brand> brandList);
    List<Brand> toDomain(List<BrandDto> brandDtoList);
}

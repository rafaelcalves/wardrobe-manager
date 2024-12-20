package br.com.correa.wardrobemanager.infra.gateways.mongo.brand;

import br.com.correa.wardrobemanager.domain.entities.Brand;
import br.com.correa.wardrobemanager.infra.persistence.mongo.brand.BrandDocument;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BrandDocumentMapper {
    BrandDocument toDocument(Brand brand);
    BrandDocument toDocument(@MappingTarget BrandDocument target, Brand source);
    Brand toDomain(BrandDocument brandDocument);

    List<Brand> toDomain(List<BrandDocument> brandDocumentList);
}

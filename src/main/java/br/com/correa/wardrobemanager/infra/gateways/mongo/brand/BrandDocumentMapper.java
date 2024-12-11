package br.com.correa.wardrobemanager.infra.gateways.mongo.brand;

import br.com.correa.wardrobemanager.domain.entities.Brand;
import br.com.correa.wardrobemanager.infra.persistence.mongo.brand.BrandDocument;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BrandDocumentMapper {
    BrandDocument toDocument(Brand brand);
    Brand toDomain(BrandDocument brandDocument);

    List<BrandDocument> toDocument(List<Brand> brandList);
    List<Brand> toDomain(List<BrandDocument> brandDocumentList);
}

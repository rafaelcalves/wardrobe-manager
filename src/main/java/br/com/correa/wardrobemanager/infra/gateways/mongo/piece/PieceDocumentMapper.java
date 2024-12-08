package br.com.correa.wardrobemanager.infra.gateways.mongo.piece;

import br.com.correa.wardrobemanager.domain.entities.Piece;
import br.com.correa.wardrobemanager.infra.persistence.mongo.piece.PieceDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PieceDocumentMapper {
    @Mapping(target = "brandCode", source = "brand.code")
    @Mapping(target = "categoryValue", source = "category.value")
    PieceDocument toDocument(Piece piece);

    @Mapping(target = "brand.code", source = "brandCode")
    @Mapping(target = "category.value", source = "categoryValue")
    Piece toDomain(PieceDocument pieceDocument);
}

package br.com.correa.wardrobemanager.infra.controller.piece;

import br.com.correa.wardrobemanager.domain.entities.Piece;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PieceDtoMapper {

    @Mapping(target = "brandCode", source = "brand.code")
    @Mapping(target = "categoryValue", source = "category.value")
    PieceDto toDto(Piece piece);

    @Mapping(target = "brand.code", source = "brandCode")
    @Mapping(target = "category.value", source = "categoryValue")
    Piece toDomain(PieceDto pieceDto);
}
package br.com.correa.wardrobemanager.infra.controller.piece;

import br.com.correa.wardrobemanager.domain.entities.Piece;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PieceDtoMapper {

    @Mapping(target = "brandCode", source = "brand.code")
    @Mapping(target = "categoryCode", source = "category.code")
    PieceDto toDto(Piece piece);

    @Mapping(target = "brand.code", source = "brandCode")
    @Mapping(target = "category.code", source = "categoryCode")
    Piece toDomain(PieceDto pieceDto);
}
package br.com.correa.wardrobemanager.infra.controller.piece;

import br.com.correa.wardrobemanager.domain.entities.Piece;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PieceDtoMapper {
    PieceDto toDto(Piece piece);
    Piece toDomain(PieceDto pieceDto);
}
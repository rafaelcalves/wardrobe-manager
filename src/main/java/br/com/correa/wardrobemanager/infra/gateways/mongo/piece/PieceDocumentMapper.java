package br.com.correa.wardrobemanager.infra.gateways.mongo.piece;

import br.com.correa.wardrobemanager.application.exceptions.ElementNotFoundException;
import br.com.correa.wardrobemanager.application.usecases.brand.BrandSearch;
import br.com.correa.wardrobemanager.domain.entities.Brand;
import br.com.correa.wardrobemanager.domain.entities.Piece;
import br.com.correa.wardrobemanager.infra.persistence.mongo.piece.PieceDocument;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(uses = BrandSearch.class, componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class PieceDocumentMapper {
    protected BrandSearch brandSearch;

    @Autowired
    public void setBrandSearch(BrandSearch brandSearch) {
        this.brandSearch = brandSearch;
    }

    @Mapping(target = "brandCode", source = "brand.code")
    @Mapping(target = "categoryCode", source = "category.code")
    abstract PieceDocument toDocument(Piece piece);

    @Mapping(target = "brand", source = "brandCode", qualifiedByName = "codeToBrand")
    @Mapping(target = "category.code", source = "categoryCode")
    abstract Piece toDomain(PieceDocument pieceDocument);

    @Named("codeToBrand")
    protected Brand map(String brandCode) throws ElementNotFoundException {
        return brandSearch.getByCode(brandCode);
    }
}

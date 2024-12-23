package br.com.correa.wardrobemanager.infra.controller.piece;

import br.com.correa.wardrobemanager.application.exceptions.ElementNotFoundException;
import br.com.correa.wardrobemanager.application.usecases.brand.BrandSearch;
import br.com.correa.wardrobemanager.application.usecases.category.CategorySearch;
import br.com.correa.wardrobemanager.domain.entities.Brand;
import br.com.correa.wardrobemanager.domain.entities.Category;
import br.com.correa.wardrobemanager.domain.entities.Piece;
import br.com.correa.wardrobemanager.config.MapStructConfig;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(config = MapStructConfig.class)
public abstract class PieceDtoMapper {
    protected BrandSearch brandSearch;
    protected CategorySearch categorySearch;

    @Autowired
    public void setBrandSearch(BrandSearch brandSearch) {
        this.brandSearch = brandSearch;
    }

    @Autowired
    public void setCategorySearch(CategorySearch categorySearch) {
        this.categorySearch = categorySearch;
    }

    @Mapping(target = "brandCode", source = "brand.code")
    @Mapping(target = "categoryCode", source = "category.code")
    public abstract PieceDto toDto(Piece piece);
    public abstract List<PieceDto> toDto(List<Piece> pieceList);

    @Mapping(target = "brand", source = "brandCode", qualifiedByName = "codeToBrand")
    @Mapping(target = "category", source = "categoryCode", qualifiedByName = "codeToCategory")
    public abstract Piece toDomain(PieceDto pieceDto);

    @Named("codeToBrand")
    protected Brand mapBrand(String brandCode) throws ElementNotFoundException {
        return brandSearch.getByCode(brandCode);
    }

    @Named("codeToCategory")
    protected Category mapCategory(String categoryCode) throws ElementNotFoundException {
        return categorySearch.getByCode(categoryCode);
    }
}
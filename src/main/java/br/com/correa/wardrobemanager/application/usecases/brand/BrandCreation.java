package br.com.correa.wardrobemanager.application.usecases.brand;

import br.com.correa.wardrobemanager.application.exceptions.ElementCodeConflictException;
import br.com.correa.wardrobemanager.application.exceptions.ElementNotFoundException;
import br.com.correa.wardrobemanager.application.gateways.BrandDSGateway;
import br.com.correa.wardrobemanager.domain.entities.Brand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandCreation {
    private final BrandDSGateway dsGateway;
    private final BrandSearch brandSearch;

    public Brand create(Brand brand) throws ElementCodeConflictException {
        try {
            brandSearch.getByCode(brand.getCode());
            throw new ElementCodeConflictException("Brand code exists");
        } catch (ElementNotFoundException e) {
            return dsGateway.createBrand(brand);
        }
    }
}
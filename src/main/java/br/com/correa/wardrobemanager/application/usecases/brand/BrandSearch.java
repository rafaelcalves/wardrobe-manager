package br.com.correa.wardrobemanager.application.usecases.brand;

import br.com.correa.wardrobemanager.application.exceptions.ElementNotFoundException;
import br.com.correa.wardrobemanager.application.gateways.BrandDSGateway;
import br.com.correa.wardrobemanager.domain.entities.Brand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandSearch {
    private final BrandDSGateway dsGateway;

    public Brand getByCode(String code) throws ElementNotFoundException {
        return dsGateway.getBrandByCode(code).orElseThrow(() -> new ElementNotFoundException("Brand not found"));
    }
}

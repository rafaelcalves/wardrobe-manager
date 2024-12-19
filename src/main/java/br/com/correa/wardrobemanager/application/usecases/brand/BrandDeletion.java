package br.com.correa.wardrobemanager.application.usecases.brand;

import br.com.correa.wardrobemanager.application.exceptions.ElementNotFoundException;
import br.com.correa.wardrobemanager.application.gateways.BrandDSGateway;
import br.com.correa.wardrobemanager.domain.entities.Brand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrandDeletion {
    private final BrandDSGateway dsGateway;

    public Brand delete(String code) throws ElementNotFoundException {
        return dsGateway.deleteBrand(code).orElseThrow(() -> new ElementNotFoundException("Brand not found"));
    }
}

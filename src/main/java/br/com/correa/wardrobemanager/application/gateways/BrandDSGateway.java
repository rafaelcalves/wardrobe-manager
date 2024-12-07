package br.com.correa.wardrobemanager.application.gateways;

import br.com.correa.wardrobemanager.domain.entities.Brand;

public interface BrandDSGateway {
    Brand createBrand(Brand brand);
}

package br.com.correa.wardrobemanager.application.gateways;

import br.com.correa.wardrobemanager.domain.entities.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandDSGateway {
    Brand createBrand(Brand brand);
    Optional<Brand> getBrandByCode(String brandCode);
    List<Brand> getAllBrands();
}

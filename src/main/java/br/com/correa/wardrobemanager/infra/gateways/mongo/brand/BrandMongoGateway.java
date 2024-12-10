package br.com.correa.wardrobemanager.infra.gateways.mongo.brand;

import br.com.correa.wardrobemanager.application.gateways.BrandDSGateway;
import br.com.correa.wardrobemanager.domain.entities.Brand;
import br.com.correa.wardrobemanager.infra.persistence.mongo.brand.BrandDocument;
import br.com.correa.wardrobemanager.infra.persistence.mongo.brand.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandMongoGateway implements BrandDSGateway {
    private final BrandRepository brandRepository;
    private final BrandDocumentMapper brandDocumentMapper;

    @Override
    public Brand createBrand(Brand brand) {
        BrandDocument document = brandDocumentMapper.toDocument(brand);
        return brandDocumentMapper.toDomain(brandRepository.save(document));
    }

    @Override
    public Optional<Brand> getBrandByCode(String brandCode) {
        return Optional.empty();
    }
}

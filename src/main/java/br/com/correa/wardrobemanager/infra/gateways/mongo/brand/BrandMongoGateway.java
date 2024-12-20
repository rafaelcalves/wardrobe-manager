package br.com.correa.wardrobemanager.infra.gateways.mongo.brand;

import br.com.correa.wardrobemanager.application.gateways.BrandDSGateway;
import br.com.correa.wardrobemanager.domain.entities.Brand;
import br.com.correa.wardrobemanager.infra.persistence.mongo.brand.BrandDocument;
import br.com.correa.wardrobemanager.infra.persistence.mongo.brand.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
        BrandDocument brandDocument = brandRepository.findByCode(brandCode).orElse(null);
        return Optional.ofNullable(brandDocumentMapper.toDomain(brandDocument));
    }

    @Override
    public List<Brand> getAllBrands() {
        List<BrandDocument> brandDocumentList = Objects.requireNonNullElse(brandRepository.findAll(), Collections.emptyList());
        return brandDocumentMapper.toDomain(brandDocumentList);
    }

    @Override
    public Optional<Brand> deleteBrand(String brandCode) {
        BrandDocument brandDocument = brandRepository.deleteByCode(brandCode).orElse(null);
        return Optional.ofNullable(brandDocumentMapper.toDomain(brandDocument));
    }

    @Override
    public Optional<Brand> editBrand(String brandCode, Brand brand) {
        Optional<BrandDocument> brandDocument = brandRepository.findByCode(brandCode);
        if(brandDocument.isEmpty()) return Optional.empty();
        BrandDocument document = brandDocumentMapper.toDocument(brandDocument.get(), brand);
        return Optional.of(brandDocumentMapper.toDomain(brandRepository.save(document)));
    }
}

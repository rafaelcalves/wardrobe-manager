package br.com.correa.wardrobemanager.infra.persistence.mongo.brand;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BrandRepository extends MongoRepository<BrandDocument, String> {
    public Optional<BrandDocument> findByCode(String code);
}

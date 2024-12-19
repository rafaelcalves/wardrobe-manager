package br.com.correa.wardrobemanager.infra.persistence.mongo.brand;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BrandRepository extends MongoRepository<BrandDocument, String> {
    Optional<BrandDocument> findByCode(String code);
    Optional<BrandDocument> deleteByCode(String code);
}

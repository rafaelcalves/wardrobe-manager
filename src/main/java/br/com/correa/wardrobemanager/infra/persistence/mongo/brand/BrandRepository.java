package br.com.correa.wardrobemanager.infra.persistence.mongo.brand;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BrandRepository extends MongoRepository<BrandDocument, String> {
}

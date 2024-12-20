package br.com.correa.wardrobemanager.infra.persistence.mongo.category;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CategoryRepository extends MongoRepository<CategoryDocument, String> {
    Optional<CategoryDocument> findByCode(String code);

    Optional<CategoryDocument> deleteByCode(String code);
}

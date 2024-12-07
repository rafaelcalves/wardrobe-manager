package br.com.correa.wardrobemanager.infra.persistence.mongo.category;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<CategoryDocument, String> {
}

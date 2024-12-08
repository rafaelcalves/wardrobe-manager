package br.com.correa.wardrobemanager.infra.persistence.mongo.piece;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PieceRepository extends MongoRepository<PieceDocument, String> {
}

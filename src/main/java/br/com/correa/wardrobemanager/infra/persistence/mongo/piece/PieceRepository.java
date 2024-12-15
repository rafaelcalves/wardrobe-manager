package br.com.correa.wardrobemanager.infra.persistence.mongo.piece;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PieceRepository extends MongoRepository<PieceDocument, String> {
    Optional<PieceDocument> findByCode(String code);
}

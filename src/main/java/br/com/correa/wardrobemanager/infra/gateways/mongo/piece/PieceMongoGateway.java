package br.com.correa.wardrobemanager.infra.gateways.mongo.piece;

import br.com.correa.wardrobemanager.application.gateways.PieceDSGateway;
import br.com.correa.wardrobemanager.domain.entities.Piece;
import br.com.correa.wardrobemanager.infra.persistence.mongo.piece.PieceDocument;
import br.com.correa.wardrobemanager.infra.persistence.mongo.piece.PieceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PieceMongoGateway implements PieceDSGateway {
    private final PieceDocumentMapper pieceDocumentMapper;
    private final PieceRepository pieceRepository;

    @Override
    public Piece createPiece(Piece piece) {
        PieceDocument document = pieceDocumentMapper.toDocument(piece);
        return pieceDocumentMapper.toDomain(pieceRepository.save(document));
    }

    @Override
    public Optional<Piece> getPieceByCode(String pieceCode) {
        PieceDocument pieceDocument = pieceRepository.findByCode(pieceCode).orElse(null);
        return Optional.ofNullable(pieceDocumentMapper.toDomain(pieceDocument));
    }

    @Override
    public List<Piece> getAllPieces() {
        List<PieceDocument> pieceDocumentList = Objects.requireNonNullElse(pieceRepository.findAll(), Collections.emptyList());
        return pieceDocumentMapper.toDomain(pieceDocumentList);
    }
}

package br.com.correa.wardrobemanager.infra.gateways.mongo.piece;

import br.com.correa.wardrobemanager.application.gateways.PieceDSGateway;
import br.com.correa.wardrobemanager.domain.entities.Piece;
import br.com.correa.wardrobemanager.infra.persistence.mongo.piece.PieceDocument;
import br.com.correa.wardrobemanager.infra.persistence.mongo.piece.PieceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}

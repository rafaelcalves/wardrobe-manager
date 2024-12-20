package br.com.correa.wardrobemanager.application.gateways;

import br.com.correa.wardrobemanager.domain.entities.Piece;

import java.util.List;
import java.util.Optional;

public interface PieceDSGateway {
    Optional<Piece> deletePiece(String pieceCode);
    Piece createPiece(Piece piece);
    Optional<Piece> getPieceByCode(String pieceCode);
    List<Piece> getAllPieces();
}

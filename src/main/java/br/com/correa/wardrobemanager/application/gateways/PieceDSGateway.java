package br.com.correa.wardrobemanager.application.gateways;

import br.com.correa.wardrobemanager.domain.entities.Piece;

public interface PieceDSGateway {
    Piece createPiece(Piece piece);
}

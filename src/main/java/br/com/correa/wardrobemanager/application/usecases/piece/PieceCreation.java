package br.com.correa.wardrobemanager.application.usecases.piece;

import br.com.correa.wardrobemanager.application.gateways.PieceDSGateway;
import br.com.correa.wardrobemanager.domain.entities.Piece;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PieceCreation {
    private final PieceDSGateway dsGateway;

    public Piece create(Piece piece) {
        return dsGateway.createPiece(piece);
    }
}

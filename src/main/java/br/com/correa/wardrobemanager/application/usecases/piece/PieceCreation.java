package br.com.correa.wardrobemanager.application.usecases.piece;

import br.com.correa.wardrobemanager.application.exceptions.ElementCodeConflictException;
import br.com.correa.wardrobemanager.application.exceptions.ElementNotFoundException;
import br.com.correa.wardrobemanager.application.gateways.PieceDSGateway;
import br.com.correa.wardrobemanager.domain.entities.Piece;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PieceCreation {
    private final PieceDSGateway dsGateway;
    private final PieceSearch pieceSearch;

    public Piece create(Piece piece) throws ElementCodeConflictException {
        try {
            pieceSearch.getByCode(piece.getCode());
            throw new ElementCodeConflictException("Piece code exists");
        } catch (ElementNotFoundException e) {
            return dsGateway.createPiece(piece);
        }
    }
}

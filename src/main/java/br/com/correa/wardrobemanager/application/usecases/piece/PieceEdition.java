package br.com.correa.wardrobemanager.application.usecases.piece;

import br.com.correa.wardrobemanager.application.exceptions.ElementNotFoundException;
import br.com.correa.wardrobemanager.application.gateways.PieceDSGateway;
import br.com.correa.wardrobemanager.domain.entities.Piece;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PieceEdition {
    private final PieceDSGateway dsGateway;

    public Piece edit(String code, Piece piece) throws ElementNotFoundException {
        return dsGateway.editPiece(code, piece).orElseThrow(() -> new ElementNotFoundException("Piece not found"));
    }
}
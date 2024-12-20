package br.com.correa.wardrobemanager.application.usecases.piece;

import br.com.correa.wardrobemanager.application.exceptions.ElementNotFoundException;
import br.com.correa.wardrobemanager.application.gateways.PieceDSGateway;
import br.com.correa.wardrobemanager.domain.entities.Piece;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PieceDeletion {
    private final PieceDSGateway dsGateway;

    public Piece delete(String code) throws ElementNotFoundException {
        return dsGateway.deletePiece(code).orElseThrow(() -> new ElementNotFoundException("Piece not found"));
    }
}

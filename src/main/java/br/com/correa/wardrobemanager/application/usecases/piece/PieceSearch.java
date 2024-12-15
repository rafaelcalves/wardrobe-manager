package br.com.correa.wardrobemanager.application.usecases.piece;

import br.com.correa.wardrobemanager.application.exceptions.ElementNotFoundException;
import br.com.correa.wardrobemanager.application.gateways.PieceDSGateway;
import br.com.correa.wardrobemanager.domain.entities.Piece;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PieceSearch {
    private final PieceDSGateway dsGateway;

    public Piece getByCode(String code) throws ElementNotFoundException {
        return dsGateway.getPieceByCode(code).orElseThrow(() -> new ElementNotFoundException("Piece not found"));
    }

    public List<Piece> getAll() {
        return dsGateway.getAllPieces();
    }
}

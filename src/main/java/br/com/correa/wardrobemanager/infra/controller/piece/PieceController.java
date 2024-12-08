package br.com.correa.wardrobemanager.infra.controller.piece;

import br.com.correa.wardrobemanager.application.usecases.piece.PieceCreation;
import br.com.correa.wardrobemanager.domain.entities.Piece;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/piece")
@RequiredArgsConstructor
public class PieceController {
    private final PieceDtoMapper pieceDtoMapper;
    private final PieceCreation pieceCreation;

    @PostMapping
    public PieceDto create(@RequestBody PieceDto pieceDto) {
        Piece domain = pieceDtoMapper.toDomain(pieceDto);
        return pieceDtoMapper.toDto(pieceCreation.create(domain));
    }
}

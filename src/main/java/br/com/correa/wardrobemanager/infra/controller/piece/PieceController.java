package br.com.correa.wardrobemanager.infra.controller.piece;

import br.com.correa.wardrobemanager.application.exceptions.ElementCodeConflictException;
import br.com.correa.wardrobemanager.application.exceptions.ElementNotFoundException;
import br.com.correa.wardrobemanager.application.usecases.piece.PieceCreation;
import br.com.correa.wardrobemanager.application.usecases.piece.PieceDeletion;
import br.com.correa.wardrobemanager.application.usecases.piece.PieceEdition;
import br.com.correa.wardrobemanager.application.usecases.piece.PieceSearch;
import br.com.correa.wardrobemanager.domain.entities.Piece;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/piece")
@RequiredArgsConstructor
public class PieceController {
    private final PieceDtoMapper pieceDtoMapper;

    private final PieceCreation pieceCreation;
    private final PieceSearch pieceSearch;
    private final PieceDeletion pieceDeletion;
    private final PieceEdition pieceEdition;

    @PostMapping
    public PieceDto create(@RequestBody PieceDto pieceDto) throws ElementCodeConflictException {
        Piece domain = pieceDtoMapper.toDomain(pieceDto);
        return pieceDtoMapper.toDto(pieceCreation.create(domain));
    }

    @GetMapping("/{code}")
    public PieceDto getByCode(@PathVariable String code) throws ElementNotFoundException {
        return pieceDtoMapper.toDto(pieceSearch.getByCode(code));
    }

    @GetMapping
    public List<PieceDto> getAll() {
        return pieceDtoMapper.toDto(pieceSearch.getAll());
    }

    @DeleteMapping("/{code}")
    public PieceDto delete(@PathVariable String code) throws ElementNotFoundException {
        return pieceDtoMapper.toDto(pieceDeletion.delete(code));
    }

    @PutMapping("/{code}")
    public PieceDto edit(@PathVariable String code, @RequestBody PieceDto pieceDto) throws ElementNotFoundException {
        Piece domain = pieceDtoMapper.toDomain(pieceDto);
        return pieceDtoMapper.toDto(pieceEdition.edit(code, domain));
    }
}

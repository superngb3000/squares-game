package org.superngb.squaresservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.superngb.Board;
import org.superngb.GameEngine;
import org.superngb.PieceColorEnum;
import org.superngb.squaresservice.dto.BoardDto;
import org.superngb.squaresservice.dto.SimpleMoveDto;

@Service
public class GameService implements IGameService {

    @Override
    public ResponseEntity<?> getNextMove(BoardDto boardDto) {
        int size = boardDto.size();
        String gridData = boardDto.data();
        String playerColor = boardDto.nextPlayerColor().toUpperCase();
        Board board = new Board(size, gridData);
        PieceColorEnum pieceColor = PieceColorEnum.valueOf(playerColor);
        GameEngine gameEngine = new GameEngine();
        int[] move = gameEngine.getMove(board, pieceColor);
        if (move == null) {
            return new ResponseEntity<>("No move available", HttpStatus.NO_CONTENT);
        }
        SimpleMoveDto simpleMoveDto = new SimpleMoveDto(move[0], move[1], playerColor);
        return new ResponseEntity<>(simpleMoveDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getStatus() {
        return null;
    }
}

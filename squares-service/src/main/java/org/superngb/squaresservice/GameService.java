package org.superngb.squaresservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.superngb.*;
import org.superngb.squaresservice.dto.*;

@Service
public class GameService implements IGameService {

    private final GameEngine gameEngine;

    public GameService() {
        this.gameEngine = new GameEngine();
    }

    @Override
    public ResponseEntity<?> getNextMove(BoardDto boardDto) {
        int size = boardDto.size();
        String gridData = boardDto.data();
        String playerColor = boardDto.nextPlayerColor().toUpperCase();
        Board board = new Board(size, gridData);
        PieceColorEnum pieceColor = PieceColorEnum.valueOf(playerColor);
        int[] move = gameEngine.getMove(board, pieceColor);
        if (move == null) {
            return new ResponseEntity<>("No move available", HttpStatus.NO_CONTENT);
        }
        SimpleMoveDto simpleMoveDto = new SimpleMoveDto(move[0], move[1], playerColor);
        return new ResponseEntity<>(simpleMoveDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getStatus(BoardDto boardDto) {
        int size = boardDto.size();
        String gridData = boardDto.data();
        Board board = new Board(size, gridData);
        GameStatus gameStatus = gameEngine.getGameStatus(board);
        GameStatusEnum status = gameStatus.getGameStatusEnum();
        String winnerColor = null;
        if (status == GameStatusEnum.WIN) {
            winnerColor = gameStatus.getWinnerColor().name();
        }
        GameStatusDto gameStatusDto = new GameStatusDto(status.name(), winnerColor);
        return new ResponseEntity<>(gameStatusDto, HttpStatus.OK);
    }
}

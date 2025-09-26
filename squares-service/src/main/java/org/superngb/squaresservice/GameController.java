package org.superngb.squaresservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.superngb.squaresservice.dto.BoardDto;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/api/nextMove")
    public ResponseEntity<?> nextMove(@RequestBody BoardDto boardDto) {
        return gameService.getNextMove(boardDto);
    }

    @PostMapping("/api/gameStatus")
    public ResponseEntity<?> getGameStatus(@RequestBody BoardDto boardDto) {
        return gameService.getStatus(boardDto);
    }
}

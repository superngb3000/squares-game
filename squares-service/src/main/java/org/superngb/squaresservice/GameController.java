package org.superngb.squaresservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.superngb.squaresservice.dto.BoardDto;

@RestController()
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/api/nextMove")
    public ResponseEntity<?> nextMove(@RequestBody BoardDto boardDto) {
        return gameService.getNextMove(boardDto);
    }

    @GetMapping("/api/gameStatus")
    public ResponseEntity<?> getGameStatus() {
        return gameService.getStatus();
    }
}

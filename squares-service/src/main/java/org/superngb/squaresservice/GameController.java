package org.superngb.squaresservice;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.superngb.squaresservice.dto.BoardDto;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class GameController {

    private final IGameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/api/nextMove")
    public ResponseEntity<?> nextMove(@Valid @RequestBody BoardDto boardDto) {
        return gameService.getNextMove(boardDto);
    }

    @PostMapping("/api/gameStatus")
    public ResponseEntity<?> getGameStatus(@Valid @RequestBody BoardDto boardDto) {
        return gameService.getStatus(boardDto);
    }
}

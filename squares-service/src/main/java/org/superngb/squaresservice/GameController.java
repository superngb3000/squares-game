package org.superngb.squaresservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.superngb.squaresservice.dto.BoardDto;

@RestController("/api")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/nextMove")
    public ResponseEntity<?> nextMove(@RequestBody BoardDto boardDto) {
        return gameService.getNextMove(boardDto);
    }
}

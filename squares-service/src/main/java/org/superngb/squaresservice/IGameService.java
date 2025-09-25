package org.superngb.squaresservice;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.superngb.squaresservice.dto.BoardDto;

@Component
public interface IGameService {
    ResponseEntity<?> getNextMove(BoardDto boardDto);

    ResponseEntity<?> getStatus();
}

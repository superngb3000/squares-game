package org.superngb.squaresservice.dto;

public record BoardDto(
        Integer size,
        String data,
        String nextPlayerColor
) {
}

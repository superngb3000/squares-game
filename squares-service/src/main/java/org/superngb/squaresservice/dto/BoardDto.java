package org.superngb.squaresservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record BoardDto(
        @NotNull
        @Min(value = 3)
        Integer size,
        @NotNull
        @Pattern(regexp = "^[wbWB\\s\\n\\r]+$")
        String data,
        @Pattern(regexp = "^[wbWB]{1}$")
        String nextPlayerColor
) {
}

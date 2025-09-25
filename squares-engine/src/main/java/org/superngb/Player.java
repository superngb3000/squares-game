package org.superngb;

public class Player {
    private final PlayerTypeEnum playerTypeEnum;
    private final PieceColorEnum color;

    public Player(PlayerTypeEnum playerTypeEnum, PieceColorEnum color) {
        this.playerTypeEnum = playerTypeEnum;
        this.color = color;
    }

    public PlayerTypeEnum getPlayerType() {
        return playerTypeEnum;
    }

    public PieceColorEnum getColor() {
        return color;
    }
}

package org.superngb;

public class Player {
    private final PlayerType playerType;
    private final char color;

    public Player(PlayerType playerType, char color) {
        this.playerType = playerType;
        this.color = color;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public char getColor() {
        return color;
    }
}

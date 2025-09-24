package org.superngb;

public class Player {
    private final PlayerType playerType;
    private final PieceColor color;

    public Player(PlayerType playerType, PieceColor color) {
        this.playerType = playerType;
        this.color = color;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }

    public PieceColor getColor() {
        return color;
    }
}

package org.superngb;

public class GameStatus {
    private GameStatusEnum gameStatusEnum;
    private PieceColorEnum winnerColor;

    public GameStatus() {
        this.gameStatusEnum = GameStatusEnum.NONE;
    }

    public void setOngoing() {
        this.gameStatusEnum = GameStatusEnum.ONGOING;
    }

    public void setDraw() {
        this.gameStatusEnum = GameStatusEnum.DRAW;
    }
    
    public void setWin(PieceColorEnum winner) {
        this.gameStatusEnum = GameStatusEnum.WIN;
        this.winnerColor = winner;
    }

    public GameStatusEnum getGameStatusEnum() {
        return gameStatusEnum;
    }

    public PieceColorEnum getWinnerColor() {
        return winnerColor;
    }
}

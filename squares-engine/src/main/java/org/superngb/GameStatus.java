package org.superngb;

public class GameStatus {
    private GameStatusEnum gameStatusEnum;
    private Player winner;

    public GameStatus() {
        this.gameStatusEnum = GameStatusEnum.ONGOING;
    }

    public void setDraw() {
        this.gameStatusEnum = GameStatusEnum.DRAW;
    }
    
    public void setWin(Player winner) {
        this.gameStatusEnum = GameStatusEnum.WIN;
        this.winner = winner;
    }

    public GameStatusEnum getGameStatusEnum() {
        return gameStatusEnum;
    }

    public Player getWinner() {
        return winner;
    }
}

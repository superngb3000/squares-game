package org.superngb;

import java.util.Random;

public class GameEngine {
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private boolean gameStarted = false;
    private int[] lastMove = new int[2];

    public void startGame(int size, Player player1, Player player2) {
        this.board = new Board(size);
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.gameStarted = true;
        System.out.println("New game started");

        if (currentPlayer.getPlayerType() == PlayerType.COMP) {
            int[] move = getMove();
            move(move[0], move[1]);
        }
    }

    public void move(int x, int y) {
        if (!gameStarted || !board.isFree(x, y)) {
            return;
        }
        lastMove[0] = x;
        lastMove[1] = y;

        board.place(x, y, currentPlayer.getColor());

        if (checkWin(currentPlayer.getColor())) {
            System.out.printf("Game finished. %s wins!%n", currentPlayer.getColor());
            gameStarted = false;
            return;
        }

        if (board.isFull()) {
            System.out.println("Game finished. Draw");
            gameStarted = false;
            return;
        }

        currentPlayer = (currentPlayer == player1) ? player2 : player1;

        if (currentPlayer.getPlayerType() == PlayerType.COMP) {
            int[] move = getMove();
            move(move[0], move[1]);
        }
    }

    private boolean checkWin(PieceColor color) {
        int n = board.getSize();
        PieceColor[][] grid = board.getGrid();
        int x = lastMove[0], y = lastMove[1];
        int dx, dy, cx1, cx2, cy1, cy2;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (x == i && y == j || grid[i][j] != color) continue;

                dx = i - x;
                dy = j - y;

                cx1 = x - dy;
                cy1 = y + dx;
                cx2 = i - dy;
                cy2 = j + dx;

                if (inBounds(cx1, cy1, n) && inBounds(cx2, cy2, n)
                        && grid[cx1][cy1] == color && grid[cx2][cy2] == color) return true;

                cx1 = x + dy;
                cy1 = y - dx;
                cx2 = i + dy;
                cy2 = j - dx;

                if (inBounds(cx1, cy1, n) && inBounds(cx2, cy2, n)
                        && grid[cx1][cy1] == color && grid[cx2][cy2] == color) return true;
            }
        }
        return false;
    }

    private boolean inBounds(int x, int y, int n) {
        return x >= 0 && y >= 0 && x < n && y < n;
    }

    private int[] getMove() { // TODO алгоритм бота
        Random rnd = new Random();
        int x, y;
        do {
            x = rnd.nextInt(board.getSize());
            y = rnd.nextInt(board.getSize());
        } while (!board.isFree(x, y));
        return new int[]{x, y};
    }
}

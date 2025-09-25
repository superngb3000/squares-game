package org.superngb;

import java.util.Random;

public class GameEngine {
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private GameStatus gameStatus;

    public GameEngine() {
        this.gameStatus = new GameStatus();
    }

    public void startGame(int size, Player player1, Player player2) {
        this.board = new Board(size);
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.gameStatus.start();
        System.out.println("New game started");

        if (currentPlayer.getPlayerType() == PlayerTypeEnum.COMP) {
            int[] move = getMove(board, currentPlayer.getColor());
            move(move[0], move[1]);
        }
    }

    public void move(int x, int y) {
        if (gameStatus.getGameStatusEnum() != GameStatusEnum.ONGOING || !board.isFree(x, y)) {
            return;
        }

        board.place(x, y, currentPlayer.getColor());

        if (checkWin(board, currentPlayer.getColor(), x, y)) {
            System.out.printf("Game finished. %s wins!%n", currentPlayer.getColor());
            gameStatus.setWin(currentPlayer);
            return;
        }

        if (board.isFull()) {
            System.out.println("Game finished. Draw");
            gameStatus.setDraw();
            return;
        }

        currentPlayer = (currentPlayer == player1) ? player2 : player1;

        if (currentPlayer.getPlayerType() == PlayerTypeEnum.COMP) {
            int[] move = getMove(board, currentPlayer.getColor());
            move(move[0], move[1]);
        }
    }

    public int[] getMove(Board currentBoard, PieceColorEnum color) {
        int[] move;

        // Можно ли победить за ход
        move = findWinningMove(currentBoard, color);
        if (move != null) return move;

        // Можно ли заблокировать победу противника
        PieceColorEnum opponentColor = (color == PieceColorEnum.B) ? PieceColorEnum.W : PieceColorEnum.B;
        move = findWinningMove(currentBoard, opponentColor);
        if (move != null) return move;

        // Поиск наиболее перспективного хода
        move = findBestMove(currentBoard, color);
        if (move != null) return move;

        // Если ничего не получилось рандомим ход
        move = getRandomMove(currentBoard);
        return move;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    private boolean checkWin(Board currentBoard, PieceColorEnum color, int x, int y) {
        int n = currentBoard.getSize();
        PieceColorEnum[][] grid = currentBoard.getGrid();
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

    private int[] findWinningMove(Board currentBoard, PieceColorEnum color) {
        int n = currentBoard.getSize();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (currentBoard.isFree(i, j)) {
                    if (checkWin(currentBoard, color, i, j)) return new int[]{i, j};
                }
            }
        }
        return null;
    }

    private int[] getRandomMove(Board currentBoard) {
        Random rnd = new Random();
        int x, y;
        do {
            x = rnd.nextInt(currentBoard.getSize());
            y = rnd.nextInt(currentBoard.getSize());
        } while (!currentBoard.isFree(x, y));
        return new int[]{x, y};
    }

    private int[] findBestMove(Board currentBoard, PieceColorEnum color) {
        int n = currentBoard.getSize();
        PieceColorEnum[][] grid = currentBoard.getGrid().clone();
        int bestScore = -1;
        int[] bestMove = null;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (currentBoard.isFree(i, j)) {
                    grid[i][j] = color;
                    int score = countPotentialSquares(n, grid, color);
                    grid[i][j] = null;

                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new int[]{i, j};
                    }
                }
            }
        }
        return bestMove;
    }

    private int countPotentialSquares(int n, PieceColorEnum[][] grid, PieceColorEnum color) {
        int count = 0;
        for (int x1 = 0; x1 < n; x1++) {
            for (int y1 = 0; y1 < n; y1++) {
                if (grid[x1][y1] != color) continue;
                for (int x2 = 0; x2 < n; x2++) {
                    for (int y2 = 0; y2 < n; y2++) {
                        if ((x1 == x2 && y1 == y2) || grid[x2][y2] != color) continue;

                        int dx = x2 - x1;
                        int dy = y2 - y1;

                        int x3 = x1 - dy, y3 = y1 + dx;
                        int x4 = x2 - dy, y4 = y2 + dx;

                        if (inBounds(x3, y3, n) && inBounds(x4, y4, n)) {
                            if ((grid[x3][y3] == color || grid[x3][y3] == null) &&
                                    (grid[x4][y4] == color || grid[x4][y4] == null)) {
                                count++;
                            }
                        }
                    }
                }
            }
        }
        return count;
    }

}

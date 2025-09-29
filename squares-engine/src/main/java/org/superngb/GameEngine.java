package org.superngb;

import java.util.Random;

public class GameEngine {
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private boolean gameStarted = false;
    private GameStatus gameStatus;

    public void startGame(int size, Player player1, Player player2) {
        this.board = new Board(size);
        this.player1 = player1;
        this.player2 = player2;
        this.currentPlayer = player1;
        this.gameStarted = false;
        System.out.println("New game started");

        if (currentPlayer.getPlayerType() == PlayerTypeEnum.COMP) {
            int[] move = getMove(board, currentPlayer.getColor());
            move(move[0], move[1]);
        }
    }

    public void move(int x, int y) {
        if (!gameStarted || !board.isFree(x, y)) {
            return;
        }

        board.place(x, y, currentPlayer.getColor());

        if (checkWin(board, currentPlayer.getColor(), x, y)) {
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

    public GameStatus getGameStatus(Board currentBoard) {
        GameStatus gameStatus = new GameStatus();
        int n = currentBoard.getSize();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!currentBoard.isFree(i, j)) {
                    PieceColorEnum pieceColorEnum = currentBoard.get(i, j);
                    if (checkWin(currentBoard, pieceColorEnum, i, j)) {
                        gameStatus.setWin(pieceColorEnum);
                        return gameStatus;
                    }
                }
            }
        }
        if (currentBoard.isFull()) gameStatus.setDraw();
        else gameStatus.setOngoing();

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
        int[][] score = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (!currentBoard.isFree(i, j)) continue;
                evaluatePotentialSquares(currentBoard, color, i, j, score);
            }
        }

        int bestScore = -1;
        int[] bestMove = null;
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                if (!currentBoard.isFree(x, y)) continue;
                if (score[x][y] > bestScore) {
                    bestScore = score[x][y];
                    bestMove = new int[]{x, y};
                }
            }
        }
        return bestMove;
    }

    private void evaluatePotentialSquares(Board currentBoard, PieceColorEnum color, int x, int y, int[][] score) {
        int n = currentBoard.getSize();
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

        for (int[] dir : directions) {
            int dx = dir[0], dy = dir[1];

            for (int size = 1; size < n; size++) {
                int[][] corners = {
                        {x, y},
                        {x + dx * size, y},
                        {x, y + dy * size},
                        {x + dx * size, y + dy * size}
                };

                boolean valid = true;
                for (int[] c : corners) {
                    if (!inBounds(c[0], c[1], n)) {
                        valid = false;
                        break;
                    }
                }
                if (!valid) continue;

                int myCount = 0, oppCount = 0;
                for (int[] c : corners) {
                    PieceColorEnum cell = currentBoard.get(c[0], c[1]);
                    if (cell == color) myCount++;
                    else if (cell != null) oppCount++;
                }

                if (oppCount > 0) continue;

                int weight = switch (myCount) {
                    case 2 -> 20;
                    case 1 -> 5;
                    case 0 -> 1;
                    default -> 0;
                };

                score[x][y] += weight;
            }
        }
    }

}

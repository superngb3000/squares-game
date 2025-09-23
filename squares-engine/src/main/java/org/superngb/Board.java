package org.superngb;

import static org.superngb.Main.EMPTY_FIELD_SIGN;

public class Board {
    private final PieceColor[][] grid;
    private final int size;

    public Board(int size) {
        this.size = size;
        this.grid = new PieceColor[size][size];
    }

    public boolean isFree(int x, int y) {
        return grid[x][y] == null;
    }

    public void place(int x, int y, PieceColor color) {
        grid[x][y] = color;
    }

    public boolean isFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == null) return false;
            }
        }
        return true;
    }

    public void print() {
        for (PieceColor[] row : grid) {
            for (PieceColor c : row) {
                System.out.print((c == null ? EMPTY_FIELD_SIGN : c) + " ");
            }
            System.out.println();
        }
    }

    public PieceColor get(int x, int y) {
        return grid[x][y];
    }

    public PieceColor[][] getGrid() {
        return grid;
    }

    public int getSize() {
        return size;
    }
}

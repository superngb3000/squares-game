package org.superngb;

import static org.superngb.Main.EMPTY_FIELD_SIGN;

public class Board {
    private final PieceColorEnum[][] grid;
    private final int size;

    public Board(int size) {
        this.size = size;
        this.grid = new PieceColorEnum[size][size];
    }

    public Board(int size, String stringGrid) {
        this.size = size;
        this.grid = new PieceColorEnum[size][size];

        int x, y;
        stringGrid = stringGrid.replace("\n", "").toUpperCase();
        char[] tokens = stringGrid.toCharArray();
        for (int i = 0; i < size * size; i++) {
            for (PieceColorEnum pieceColorEnum : PieceColorEnum.values()) {
                if (tokens[i] == pieceColorEnum.name().charAt(0)) {
                    x = i / size;
                    y = i % size;
                    this.place(x, y, pieceColorEnum);
                }
            }
        }
    }

    public boolean isFree(int x, int y) {
        return grid[x][y] == null;
    }

    public void place(int x, int y, PieceColorEnum color) {
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
        for (int i = 0; i <= size + 1; i++) {
            System.out.print("= ");
        }
        System.out.println();
        for (PieceColorEnum[] row : grid) {
            System.out.print("| ");
            for (PieceColorEnum c : row) {
                System.out.print((c == null ? EMPTY_FIELD_SIGN : c) + " ");
            }
            System.out.println("|");
        }
        for (int i = 0; i <= size + 1; i++) {
            System.out.print("= ");
        }
        System.out.println();
    }

    public PieceColorEnum get(int x, int y) {
        return grid[x][y];
    }

    public PieceColorEnum[][] getGrid() {
        return grid;
    }

    public int getSize() {
        return size;
    }
}

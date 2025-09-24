package org.superngb;

import java.util.Scanner;

public class CommandLoop {
    private final GameEngine gameEngine;

    public CommandLoop(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String line = scanner.nextLine();
            handle(line);
        }
    }

    private void handle(String line) {
        String[] parts = line.trim().split(" ");
        if (parts.length == 0) {
            System.out.println("Incorrect command");
            return;
        }
        switch (parts[0].toUpperCase()) {
            default:
            {
                System.out.println("Incorrect command");
            }
            case "GAME":
            {
                if (parts.length < 4) {
                    System.out.println("Incorrect command");
                    return;
                }
                try {
                    int n = Integer.parseInt(parts[1].replace(",", "").trim());

                    PlayerType playerType1 = PlayerType.valueOf(parts[2].trim().toUpperCase());
                    PieceColor playerColor1 = PieceColor.valueOf(parts[3].replace(",", "").trim().toUpperCase());
                    Player player1 = new Player(playerType1, playerColor1);

                    PlayerType playerType2 = PlayerType.valueOf(parts[4].trim().toUpperCase());
                    PieceColor playerColor2 = PieceColor.valueOf(parts[5].replace(",", "").trim().toUpperCase());
                    Player player2 = new Player(playerType2, playerColor2);

                    gameEngine.startGame(n, player1, player2);
                } catch (Exception e) {
                    System.out.println("Incorrect command");
                }
                break;
            }
            case "MOVE":
            {
                if (parts.length < 3) {
                    System.out.println("Incorrect command");
                    return;
                }
                try {
                    int x = Integer.parseInt(parts[1].replace(",", "").trim());
                    int y = Integer.parseInt(parts[2].trim());
                    gameEngine.move(x, y);
                } catch (Exception e) {
                    System.out.println("Incorrect command");
                }
                break;
            }
            case "EXIT":
            {
                System.exit(0);
                break;
            }
            case "HELP":
            {
                System.out.println(
                        """
                                Commands:
                                GAME - начать новую игру.
                                MOVE - сделать ход
                                EXIT - выход (завершение работы программы)
                                HELP - вывод описания команд""");
                break;
            }
        }
    }
}

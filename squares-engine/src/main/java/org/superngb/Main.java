package org.superngb;

public class Main {
    public static char EMPTY_FIELD_SIGN = '_';

    public static void main(String[] args) {
        GameEngine gameEngine = new GameEngine();
        CommandLoop commandLoop = new CommandLoop(gameEngine);
        commandLoop.run();
    }
}
package hhccco.plutus;

import javafx.stage.Screen;

/**
 * Env class is used to global variables
 */
public class Env {
    private static final Screen screen = Screen.getPrimary();

    public static int screenMaxWidth = (int) screen.getBounds().getWidth();
    public static int screenMaxHeight = (int) screen.getBounds().getHeight();
    public static int screenWidth = screenMaxWidth / 10 * 8;
    public static int screenHeight = screenMaxHeight / 10 * 8;
}

package gb.java_core.UI;

import java.util.Scanner;

public class ConsoleUi {
    public static final int SIZE_LINE = 110;
    public static final Scanner scanner = new Scanner(System.in,"Cp866");
    private static final Menu menu = new MainMenu();
    private final MainMenu m = new MainMenu();
    private static boolean isWork = true;

    public void start() {
        clearConsole();
        printLineWithSymbol("=", SIZE_LINE, Colors.YELLOW);
        printCaption("Задачи 3-го семинара по Java-core", " ", Colors.GREEN);
        printLineWithSymbol("=", SIZE_LINE, Colors.YELLOW);
        printLineWithSymbol("-", SIZE_LINE, Colors.YELLOW);
        while (isWork) {
            print(menu.toString(), Colors.BLUE);
            menu.execute(getIntInput() - 1);
        }
    }

    public static void close() {
        scanner.close();
        isWork = false;
    }

    private void clearConsole() {
        System.out.print("\033[H\033[J");
    }

    private void printLineWithSymbol(String symbol, int sizeLine, Enum displayColor) {
        printResetln(displayColor + symbol.repeat(sizeLine) + Colors.RESET);
    }

    private void printCaption(String caption, String padSymb, Enum displayColor) {
        int spaceSize = (SIZE_LINE - caption.length()) / 2;
        String captionLine = padSymb.repeat(spaceSize) + caption + padSymb.repeat(spaceSize);
        printResetln(displayColor + captionLine + Colors.RESET);
    }

    public static void printResetln(String message) {
        System.out.println(message);
    }

    public static void println(String message, Enum displayColor) {
        System.out.println(displayColor + message + Colors.RESET);
    }

    public static void print(String message, Enum displayColor) {
        System.out.print(displayColor + message + Colors.RESET);
    }

    public static int getIntInput() {
        String input;
        do {
            print("> ", Colors.BLUE);
            input = scanner.nextLine();
            if (input.matches("\\d+")) {
                int number = Integer.parseInt(input);
                if (number >= 1 && number <= menu.size()) {
                    return number;
                }
            }
        } while (true);
    }
}

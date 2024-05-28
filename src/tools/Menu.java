/*
 * 
 * @author JF Mendoza M
 * @email jfmendozam@gmail.com
 * @email juan.mendoza@usantoto.edu.co
 * @license GNU General Public License
 * @version 1.0
*/

package tools;

import java.util.Scanner;

public class Menu {
    static Scanner sc = new Scanner(System.in);
    public final static int LEFT = 1;
    public final static int RIGHT = 2;
    public final static int CENTER = 3;
    public static char horizontalBorderChar = '-';
    public static char cornerBorderChar = '+';
    public static char verticalBorderChar = '|';

    public Menu() { 
    }

    /**
     * This method displays a menu with a title and a list of options.
     * It also includes an option to exit or return to the previous menu.
     *
     * @param title The title of the menu.
     * @param options An array of strings representing the menu options.
     * @param menuType An integer representing the type of the menu: 
     *                    1 for an exit menu, 
     *                    2 for a return menu.
     */
    public static void showMenu(String title, String[] options, int menuType) {
        int width = getMaxLen(title, options) + 8;

        String borderLine = horizontalBorder(width);
        System.out.println(borderLine);

        System.out.println(
            verticalBorderChar +
            align(title, width - 2, CENTER) +
            verticalBorderChar
        );
        System.out.println(borderLine);

        for (int i = 0; i < options.length; i++) {
            System.out.print(verticalBorderChar + " ");
            System.out.printf("%2d", (i + 1));
            System.out.println(
                ". " + 
                align(options[i], width - 8, LEFT) +
                " " +
                verticalBorderChar
            );
        }
        String exitMessage = (menuType == 1 ? "Exit" : "Return");
        System.out.println(
            verticalBorderChar +
            align("  0. " +  exitMessage, width - 2, LEFT) +
            verticalBorderChar
        );
        System.out.println(borderLine);
        System.out.print("Your option is ");
    }

    /**
     * This method calculates and returns the maximum length of a string in an array of strings.
     * It is used to determine the width of the menu based on the longest string.
     *
     * @param title The title of the menu.
     * @param options An array of strings representing the menu options.
     * @return The maximum length of a string in the array.
     */
    public static int getMaxLen(String title, String[] options) {
        int max = title.length();
        for (String item : options) {
            if (item.length() > max) max = item.length();
        }
        return max;
    }

    /**
     * This method generates a horizontal border line with the specified width and the given border character.
     * It is used to create the top and bottom borders of the menu.
     *
     * @param width The desired width of the border line.
     * @return A string representing the horizontal border line.
     */
    public static String horizontalBorder(int width) {
        String symbol = "" + horizontalBorderChar;
        return (
            cornerBorderChar + 
            symbol.repeat(width - 2) + 
            cornerBorderChar
        );
    }

    /**
     * This method aligns a given message to the left, right, or center within a specified width.
     *
     * @param message The string to be aligned.
     * @param width The desired width of the aligned message.
     * @param alignMethod An integer representing the alignment method: 
     *                      1 for left alignment, 
     *                      2 for right alignment, 
     *                      3 for center alignment.
     * @return A string representing the aligned message.
     */
    public static String align(String message, int width, int alignMethod) {
        switch (alignMethod) {
            case LEFT:
                return(message + pad(width - message.length()));
            case RIGHT:
                return(pad(width - message.length()) + message);
            case CENTER:
                int leftSize = (width - message.length()) / 2;
                int rightSize = width - message.length() - leftSize;
                return(pad(leftSize) + message + pad(rightSize));
        }
        return message;
    }

    /**
     * This method generates a padding string with the specified width.
     * It is used to align the menu options within the specified width.
     *
     * @param width The desired width of the padding string.
     * @return A string representing the padding string.
     */
    private static String pad(int width) {
        String padding = " ";
        return padding.repeat(width);
    }

    /**
     * This method clears the console screen.
     * It sends escape sequences to the console to clear the screen and then flushes the output stream.
     */
    public static void clearScreen() {
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * This method pauses the program and waits for the user to press any key.
     * It is used to give the user time to read the output or to prepare for the next action.
     */
    public static void anyKey() {
        System.out.print("Press any key to continue...");
        sc.nextLine();
    }

    /**
     * This method gets an option from the user.
     * It prompts the user to enter an option number and validates the input.
     * If the input is invalid, it displays an error message and prompts the user again.
     *
     * @param options An array of strings representing the menu options.
     * @return The selected option number.
     */
    public static int getOption(String[] options) {
        int option = -1;
        String input;
        do {
            input = sc.nextLine();
            try {
                option = Integer.parseInt(input);
            }
            catch (Exception e) {
                option = -1;
            }
            if (option < 0 || option > options.length) {
                option = -1;
            }
            if (option == -1) {
                showError("Invalid option!");
                System.out.print("Your option is ");
            }
        } while (option < 0 || option > options.length);
        return option;
    }

    /**
     * This method displays a confirmation message with a checkmark symbol and waits for the user to press any key.
     * It is used to confirm an action or a decision made by the user.
     *
     * @param message The message to be displayed as a confirmation.
     */
    public static void showConfirm(String message) {
        System.out.println("✅ " + message);
        anyKey();
    }

    /**
     * This method displays an error message with an exclamation symbol and waits for the user to press any key.
     * It is used to indicate an error or a problem encountered during the program execution.
     *
     * @param message The message to be displayed as an error.
     */
    public static void showError(String message) {
        System.out.println("❌ Error! " + message);
        anyKey();
    }

    /**
     * This method reads an integer input from the user and validates it.
     * It prompts the user to enter an integer and validates the input.
     * If the input is invalid, it displays an error message and prompts the user again.
     *
     * @param message The message to be displayed as a prompt.
     * @return The selected integer.
     */
    public static int readId(String message) {
        int id = 0;
        do {
            System.out.print("⚠️  " + message);
            try {
                id = Integer.parseInt(sc.nextLine());
                break;
            }
            catch (Exception e) {}
        } while (true);
        return id;
    }

    /**
     * This method reads a confirmation from the user.
     * It prompts the user to enter 'Y' or 'N' and validates the input.
     * If the input is invalid, it prompts the user again.
     *
     * @param message The message to be displayed as a prompt.
     * @return true if the user confirms, false otherwise.
     */
    public static boolean getConfirm(String message) {
        System.out.print("⚠️  " + message + " (Y/N) ");
        String answer = sc.nextLine().toUpperCase();
        if (answer.length() > 0) {
            return answer.charAt(0) == 'Y';    
        }
        else return false;
    }
}

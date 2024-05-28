package tools;

import java.io.Console;
import java.util.Scanner;

public class MyTool {
    static Scanner sc = new Scanner(System.in);
    public static String _line;
    
    public static void header(String message) {
        Menu.clearScreen();
        System.out.println(message);
        System.out.println("=".repeat(message.length()) + "\n");
    }

    public static String epochDateToString(long epoch, String pattern) {
        return new java.text.SimpleDateFormat(pattern)
            .format(new java.util.Date(epoch));
    }

    public static String colsHeader(String[] headers, int[] widths) {
        _line = DataTableView.getHorizontalLine(widths);
        String s = _line + "\n";
        for (int i = 0; i < headers.length; i++) {
            s += "|" + Menu.align(headers[i], widths[i], Menu.CENTER);
        }
        return s + "|\n" + _line + "\n";
    }

    public static int[] initWiths(String[] headers) {
        int[] widths = new int[headers.length];
        for (int i = 0; i < headers.length; i++) {
            widths[i] = headers[i].length();
        }
        return widths;
    }

    public static int readId(String message) {
        header("Search the " + message);
        return Menu.readId("Type the " + message + " ID: ");
    }

    public static String readName(String message, String field) {
        header("Search the " + message);
        System.out.print("Type the " + field + ": ");
        return sc.nextLine();
    }

    public static String readName() {
        System.out.print("Name: ");
        return sc.nextLine();
    }

    public static int readInt(String message, int lower) {
        System.out.print("Type the " + message + ": ");
        int number;
        try {
            number = Integer.parseInt(sc.nextLine());
            if (number > lower) return number;
        } catch (Exception e) {
            Menu.showError("Invalid " + message + "!");
        }
        return lower - 1;
    }

    public static float readFloat(String string, float lower) {
        System.out.print("Type the " + string + ": ");
        float number;
        try {
            number = Float.parseFloat(sc.nextLine());
            if (number > lower) return number;
        } catch (Exception e) {
            Menu.showError("Invalid " + string + "!");
        }
        return lower - 1;
    }

    public static String readString(String message) {
        System.out.print("Type the " + message + ": ");
        String s;
        do {
            s = sc.nextLine();
            if (s.length() > 0) break;
            Menu.showError("Invalid " + message + "!");
        } while (true);
        return s;
    }

    public static String readPassword() {
        Console console = System.console();
        String password, confirmPassword;
        do {
            System.out.print("Type the password: ");
            password = new String(console.readPassword());
            System.out.print("Confirm the password: ");
            confirmPassword = new String(console.readPassword());
            if (password.length() > 3 && password.equals(confirmPassword)) break;
            Menu.showError("Passwords do not match!");
        } while (true);
        return password;
    }

    public static String readEmail() {
        System.out.print("email: ");
        String s;
        do {
            s = sc.nextLine();
            if (s.length() > 5 && s.contains("@") && s.contains(".")) break;
            Menu.showError("Invalid email!");
        } while (true);
        return s;
    }
}

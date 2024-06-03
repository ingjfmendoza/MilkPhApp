package tools;

import java.io.Console;
import java.text.ParseException;
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

    public static long readDate() {
        int year = MyTool.readInt("Year", -5000);
        int month = getMonth();
        int day = getDay(month, year);
        try {
            return new java.text.SimpleDateFormat("dd/MM/yyyy")
                .parse(day + "/" + month + "/" + year).getTime() / 1000;
        } catch (ParseException e) {
            return 0;
        }
    }

    public static int getMonth() {
        int month = 1;
        do {
            String months[] = {
                "January", "February", "March", "April", "May", "June", "July", 
                "August", "September", "October", "November", "December"};
            Menu.showMenu("Select a month", months, Menu.RETURN);
            month = Menu.getOption(months);
            if (month >= 1 && month <= 12) return month;
        } while (true); 
    }

    public static int getDay(int month, int year) {
        int maxDay = getMaxDay(month, year);
        int day = 1;
        do {
            day = MyTool.readInt("Day", 0);
            if (day >= 1 && day <= maxDay) break;
            Menu.showError("Invalid day!");
        } while (true);
        return day;
    }

    public static int getMaxDay(int month, int year) {
        switch (month) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                return 31;
            case 4: case 6: case 9: case 11:
                return 30;
            case 2:
                if (year % 4 == 0) return 29;
                else return 28;
            default: return 0;
        }
    }

    public static String getDate(long epoch) {
        return new java.text.SimpleDateFormat("dd/MM/yyyy")
           .format(new java.util.Date(epoch * 1000));
    }
}

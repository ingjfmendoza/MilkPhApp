package view.user;

import java.io.Console;

import tools.MyTool;

public class ChangePasswordView {
    public static String getPassword() {
        System.out.println("New password.");
        return MyTool.readPassword();
    }

    public static String getOldPassword() {
        Console console = System.console();
        return new String(console.readPassword("Old Password: "));
    }
}

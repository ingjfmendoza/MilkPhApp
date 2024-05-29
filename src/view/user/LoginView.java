package view.user;

import java.io.Console;

import tools.MyTool;

public class LoginView {

    public String[] getLogin() {
        Console console = System.console();
        String[] login = new String[2];

        MyTool.header("Milk Ph App");
        showCover();
        System.out.println("User login\n");

        login[0] = MyTool.readString("Username");
        login[1] = new String(console.readPassword("Password: "));

        return login;
    }

    private void showCover() {
        System.out.println("                      ,     ,");
        System.out.println("                  ___('-&&&-')__");
        System.out.println("                 '.__./     \\__.'");
        System.out.println("     _     _     _ .-'  6  6 \\");
        System.out.println("   /` `--'( ('--` `\\         |");
        System.out.println("  /        ) )      \\ \\ _   _|");
        System.out.println(" |        ( (        | (0_._0)");
        System.out.println(" |         ) )       |/ '---'");
        System.out.println(" |        ( (        |\\_");
        System.out.println(" |         ) )       |( \\,");
        System.out.println("  \\       ((`       / )__/");
        System.out.println("   |     /:))\\     |   d");
        System.out.println("   |    /:((::\\    |");
        System.out.println("   |   |:::):::|   |");
        System.out.println("   /   \\::&&:::/   \\");
        System.out.println("   \\   /;U&::U;\\   /");
        System.out.println("    | | | u:u | | |");
        System.out.println("    | | \\     / | |");
        System.out.println("    | | _|   | _| |");
        System.out.println("    / \\\"\"`   `\"\"/ \\");
        System.out.println("   | __|       | __|");
        System.out.println("   `\"\"\"`       `\"\"\"`");
    }
}

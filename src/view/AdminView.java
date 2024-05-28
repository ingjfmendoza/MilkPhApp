package view;

import tools.Menu;

public class AdminView {

    public int showMenu(String[] options) {
        showCow();
        Menu.showMenu("Administrator menu", options, 1);
        return Menu.getOption(options);
    }

    public static void showCow() {
        Menu.clearScreen();
        System.out.println("                (__)");  
        System.out.println("        `\\------(oo)");
        System.out.println("          ||    (__)");
        System.out.println("   \\|/    ||w--||     \\|/");
    }
}

package view.milkman;

import model.Milkman;
import tools.Menu;

public class DeleteMilkmanView {

    public static boolean confirm(Milkman milkman, String username, String farmName) {
        System.out.println(milkman.details(username, farmName));
        if (Menu.getConfirm(
            "Are you sure you want to delete this milkman?"
        )) return true;
        return false;
    }

}

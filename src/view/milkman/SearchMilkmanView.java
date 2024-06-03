package view.milkman;

import model.Farms;
import model.Milkman;
import model.User;
import model.Users;
import tools.Menu;

public class SearchMilkmanView {

    public static int readUserId(Users users) {
        Menu.showMenu("Choose a user", users.options(), Menu.RETURN);
        int choice = Menu.getOption(users.options());
        if (choice > 0 && choice <= users.options().length) {
            if (User.userTypes()[users._users[choice - 1].userType - 1]
                .equals("Milkman")) {
                return users._users[choice - 1].id;
            }
            else {
                Menu.showError("The user is not milkman");
                return -1;
            }
        }
        return -1;
    }

    public static int readFarmId(Farms farms) {
        Menu.showMenu("Choose a farm", farms.options(), Menu.RETURN);
        int choice = Menu.getOption(farms.options());
        if (choice > 0 && choice <= farms.options().length) {
            return farms._farms[choice - 1].id;
        }
        return -1;
    }

    public static void show(Milkman milkman, String username, String farmName) {
        System.out.println(milkman.details(username, farmName));
        Menu.anyKey();
    }

}

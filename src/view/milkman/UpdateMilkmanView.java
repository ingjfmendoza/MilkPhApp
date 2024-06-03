package view.milkman;

import model.Farm;
import model.Farms;
import model.Milkman;
import model.Milkmen;
import model.User;
import model.Users;
import tools.Menu;

public class UpdateMilkmanView {

    public static Milkman getData(Milkman milkman, Milkmen milkmen, Users users, Farms farms) {
        int choice;
        do {
            if (milkman.userId == -1) return null;
            User user = users.get(milkman.userId);
            Farm farm = farms.get(milkman.farmId);
            Menu.showMenu(
                " Update milkman", 
                milkman.options(user.username, farm.name), 
                Menu.RETURN
            );
            choice = Menu.getOption(milkman.options(user.username, farm.name));
            switch (choice) {
                case 1: milkman.userId = SearchMilkmanView.readUserId(users); break;
                case 2: milkman.farmId = SearchMilkmanView.readFarmId(farms); break;
            }
        } while (choice != 0);

        Milkman m = milkmen.get(milkman.userId, milkman.farmId);
        if (m != null && m.id != milkman.id) {
            Menu.showError("Milkman already exists!");
            return null;
        }
        else return milkman;
    }
}

package view.user;

import model.User;
import model.Users;
import tools.Menu;
import tools.MyTool;

public class UpdateUserView {

    public static int getId() {
        MyTool.header("Update User");
        return MyTool.readInt("user Id", -1);
    }

    public static User getData(User user, Users users) {
        int choice;
        do {
            Menu.showMenu(" Update " + user.fullname, user.options(), 2);
            choice = Menu.getOption(user.options());
            switch (choice) {
                case 1: user.readUsername(); break;
                case 2: user.readPassword(); break;
                case 3: user.readFullname(); break;
                case 4: user.readEmail(); break;
                case 5: user.readPhone(); break;
                case 6: user.readUserType(); break;
            }

        } while (choice != 0);

        User u = users.get(user.username);
        boolean found = false;
        if (u != null && u.id != user.id) found = true;

        if (found) {
            Menu.showError("User already exists!");
            return null;
        }
        else return user;
    }
}

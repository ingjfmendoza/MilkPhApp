package view.user;

import model.User;
import tools.Menu;

public class SearchUserView {

    public static void show(User user) {
        System.out.println(user.details());
        Menu.anyKey();
    }

}

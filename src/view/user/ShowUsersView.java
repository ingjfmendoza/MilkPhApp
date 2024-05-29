package view.user;

import model.User;
import model.Users;
import tools.Menu;
import tools.MyTool;

public class ShowUsersView {

    public static void show(Users users) {
        MyTool.header("List of Users");
        if (users._users.length > 0) {
            users.updateWidths();
            System.out.print(users.header());
            for (User user : users._users) {
                System.out.print(user.tabular(users._widths));
            }
            System.out.println(users._line);
        }
        Menu.anyKey();
    }

}

package view.user;

import model.User;
import tools.Menu;
import tools.MyTool;

public class DeleteUserView {

    public static int getId() {
        MyTool.header("Delete User");
        return MyTool.readInt("user Id", -1);
    }

    public static boolean confirm(User user) {
        System.out.println(user.details());
        if (Menu.getConfirm(
            "Are you sure you want to delete this user?"
        )) return true;
        return false;
    }

}

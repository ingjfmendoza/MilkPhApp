package view.user;

import model.User;
import model.Users;
import tools.Menu;
import tools.MyTool;

public class AddUserView {

    public static User getData(Users users) {
        MyTool.header("Add User");
        User user = new User();
        user.readUsername();
        if (user.username == null) return null;
        if (users.exists(user.username)) {
            Menu.showError("User already exists!");
            return null;
        }
        user.readPassword();
        user.readFullname();
        user.readEmail();
        user.readPhone();
        user.readUserType();
        
        user.encrypt();
        return user;
    }

}

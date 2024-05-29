package controller;

import model.User;
import model.Users;
import tools.Database;
import tools.Menu;
import tools.MyTool;
import view.AdminView;
import view.user.AddUserView;
import view.user.ChangePasswordView;
import view.user.DeleteUserView;
import view.user.LoginView;
import view.user.SearchUserView;
import view.user.ShowUsersView;
import view.user.UpdateUserView;

public class UserController {
    Database _db;

    public UserController(Database _db) {
        this._db = _db;
    }

    public void menu() {
        String[] options = {
            "Show all users", "Search a user", "Add a user", 
            "Update a user", "Delete a user"
        };
        int choice;
        do {
            AdminView.showCow();
            Menu.showMenu("Users menu", options, 2);
            choice = Menu.getOption(options);
            switch (choice) {
                case 1: showAll(); break;
                case 2: search();  break;
                case 3: add();     break;
                case 4: update();  break;
                case 5: delete();  break;
                default: break;
            }
        } while(choice != 0);
    }

    private void showAll() {
        Users users = new Users(this._db, "");
        ShowUsersView.show(users);
    }

    private void search() {
        String name = MyTool.readName("user", "user");
        Users users = new Users(
            this._db, "username LIKE '%" + name + "%'"
        );
        if (users._users.length > 0) {
            SearchUserView.show(users._users[0]);
        }
        else {
            Menu.showError("User not found!");
        }
    }

    private void add() {
        Users users = new Users(_db, "");
        User user = AddUserView.getData(users);
        if (user != null) {
            if (this._db.insert(user)) {
                Menu.showConfirm("User added!");
            }
            else {
                Menu.showError("User not added!");
            }
        }
    }

    private void update() {
        int id = UpdateUserView.getId();
        if (id >= 0) {
            Users users = new Users(_db, "id = " + id);
            User user = users.get(id);
            if (user != null) {
                users = new Users(_db, "");
                user = UpdateUserView.getData(user, users);
                if (user != null) {
                    if (this._db.update(user.id, user)) {
                        Menu.showConfirm("User updated!");
                    }
                    else {
                        Menu.showError("User not updated!");
                    }
                }
            }
            else {
                Menu.showError("User not found!");
            }
        }
    }

    private void delete() {
        int id = DeleteUserView.getId();
        if (id >= 0) {
            Users users = new Users(_db, "id = " + id);
            User user = users.get(id);
            if (user != null) {
                if (DeleteUserView.confirm(user)) {
                    if (this._db.delete(user.id, "user")) {
                        Menu.showConfirm("User deleted!");
                    }
                    else {
                        Menu.showError("User not deleted!");
                    }
                }
            }
            else {
                Menu.showError("User not found!");
            }
        }
    }

    public boolean login() {
        LoginView lv = new LoginView();
        String[] login = lv.getLogin();
        if (checkLogin(login[0], login[1])) {
            createUserSession(login[0]);
            return true;
        }
        return false;
    }

    private boolean checkLogin(String username, String password) {
        Users users = new Users(this._db, "username LIKE '" + username + "'");
        if (users._users.length > 0) {
            User user = users._users[0];
            if (user.checkPassword(password)) {
                return true;
            }
            else {
                Menu.showError("Wrong password!");
            }
        }
        else {
            Menu.showError("User not found!");
        }
        return false;
    }

    private void createUserSession(String username) {
        Users users = new Users(this._db, "username LIKE '" + username + "'");
        UserSession.getInstance(users._users[0]);
    }

    public void changePassword() {
        User user = UserSession.getInstance(null).user;        
        String oldPassword = ChangePasswordView.getOldPassword();
        if (user.checkPassword(oldPassword)) {
            user.password = ChangePasswordView.getPassword();
            user.encrypt();
            if (this._db.update(user.id, user)) {
                Menu.showConfirm("Password changed!");
            }
            else {
                Menu.showError("Password not changed!");
            }
        }
        else {
            Menu.showError("Wrong password!");
        }
    }
}

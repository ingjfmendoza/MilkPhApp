package controller;

import tools.Database;

public class MainController {
    Database _db;

    public MainController(String url) {
        this._db = new Database(url);
    }

    public void login() {
        UserController uc = new UserController(_db);
        if (uc.login()) {
            int userType = UserSession.getInstance(null).user.userType;
            switch (userType) {
                case 1: {
                    AdminController ac = new AdminController(_db);
                    ac.menu();
                } break;
                case 2: break;  // companies
                case 3: break;  // milkman
                default: break;
            }
        }
    }
}

package controller;

import tools.Database;
import tools.Menu;

public class MainController {
    Database _db;

    public MainController(String url) {
        this._db = new Database(url);
    }

    public void login() {
        boolean login = true;   // TODO
        if (login) {
            int userType = 1;   // TODO
            switch (userType) {
                case 1: {
                    AdminController ac = new AdminController(_db);
                    ac.menu();
                } break;
                case 2: break;  // companies
                case 3: break;  // milkman
                default: break;
            }
            
        } else {
            Menu.showError("Login failed!");
        }
    }
}

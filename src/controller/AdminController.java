package controller;

import tools.Database;
import view.AdminView;

public class AdminController {
    Database _db;

    public AdminController(Database _db) {
        this._db = _db;
    }

    public void menu() {
        String[] options = {
            "Departments", "Municipalities", "Farms", "Users", "Milkmen",
            "Milkings", "Companies", "Milk processings", "Reports",
            "Change password"
        };
        int choice;
        AdminView av = new AdminView();
        do {
            choice = av.showMenu(options);
            switch (choice) {
                case 1: { 
                    DepartmentController dc = new DepartmentController(_db);
                    dc.menu();
                } break;
                case 2: { 
                    MunicipalityController mc = new MunicipalityController(_db);
                    mc.menu();
                } break;
                case 3: { 
                    FarmController fc = new FarmController(_db);
                    fc.menu();
                } break;
                case 4: { 
                    UserController uc = new UserController(_db);
                    uc.menu();
                } break;
                case 5: break;
                case 6: break;
                case 7: break;
                case 8: break;
                case 9: break;
                case 10: {
                    UserController uc = new UserController(_db);
                    uc.changePassword(); 
                } break;
                default: break;
            }
        } while(choice != 0);
    }

}

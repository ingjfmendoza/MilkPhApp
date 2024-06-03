package controller;

import model.Farms;
import model.Milkman;
import model.Milkmen;
import model.Users;
import tools.Database;
import tools.Menu;
import view.AdminView;
import view.milkman.DeleteMilkmanView;
import view.milkman.SearchMilkmanView;
import view.milkman.ShowMilkmenView;
import view.milkman.UpdateMilkmanView;

public class MilkmanController {
    Database _db;

    public MilkmanController(Database _db) {
        this._db = _db;
    }

    public void menu() {
        String[] options = {
            "Show all milkmen", "Search a milkman", "Add a milkman", 
            "Update a milkman", "Delete a milkman"
        };
        int choice;
        do {
            AdminView.showCow();
            Menu.showMenu("Milkmen menu", options, 2);
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
        Milkmen milkmen = new Milkmen(this._db, "");
        ShowMilkmenView.show(milkmen);
    }

    private void search() {
        int userid = SearchMilkmanView.readUserId(new Users(_db, ""));
        int farmid = SearchMilkmanView.readFarmId(new Farms(_db, ""));

        Milkmen milkmen = new Milkmen(
            this._db, "userId  = " + userid + " AND farmId = " + farmid
        );
        if (milkmen._milkmen.length > 0) {
            Milkman milkman = milkmen._milkmen[0];
            Users users = new Users(this._db, "id = " + milkman.userId);
            Farms farms = new Farms(this._db, "id = " + milkman.farmId);
            SearchMilkmanView.show(
                milkman, 
                users.get(milkman.userId).username, 
                farms.get(milkman.farmId).name
            );
        }
        else Menu.showError("Milkman not found!");
    }

    private void add() {
        int userid = SearchMilkmanView.readUserId(new Users(_db, ""));
        if (userid > 0) {
            int farmid = SearchMilkmanView.readFarmId(new Farms(_db, ""));
            if (farmid > 0) {
                Milkmen milkmen = new Milkmen(
                    this._db, "userId  = " + userid + " AND farmId = " + farmid
                );
                if (milkmen._milkmen.length == 0) {
                    if (this._db.insert(new Milkman(userid, farmid))) {
                        Menu.showConfirm("Milkman added!");
                    }
                    else {
                        Menu.showError("Milkman not added!");
                    }
                }
                else Menu.showError("The milkman already exists!");
            }
        }
    }

    private void update() {
        int userid = SearchMilkmanView.readUserId(new Users(_db, ""));
        if (userid > 0) {
            int farmid = SearchMilkmanView.readFarmId(new Farms(_db, ""));
            if (farmid > 0) {
                Milkmen milkmen = new Milkmen(
                    this._db, "userId  = " + userid + " AND farmId = " + farmid
                );
                if (milkmen._milkmen.length > 0) {
                    Milkman milkman = milkmen._milkmen[0];
                    Users users = new Users(_db, "");
                    Farms farms = new Farms(_db, "");
                    milkmen = new Milkmen(_db, "");
                    milkman = UpdateMilkmanView.getData(milkman, milkmen, users, farms);
                    if (milkman != null) {
                        if (this._db.update(milkman.id, milkman)) {
                            Menu.showConfirm("Milkman updated!");
                        }
                        else {
                            Menu.showError("Milkman not updated!");
                        }
                    }
                }
                else Menu.showError("The milkman not found!");
            }
        }
    }

    private void delete() {
        int userid = SearchMilkmanView.readUserId(new Users(_db, ""));
        if (userid > 0) {
            int farmid = SearchMilkmanView.readFarmId(new Farms(_db, ""));
            if (farmid > 0) {
                Milkmen milkmen = new Milkmen(
                    this._db, "userId  = " + userid + " AND farmId = " + farmid
                );
                if (milkmen._milkmen.length > 0) {
                    Milkman milkman = milkmen._milkmen[0];
                    Users users = new Users(_db, "id = " + userid);
                    Farms farms = new Farms(_db, "id = " + farmid);
                    if (DeleteMilkmanView.confirm(
                        milkman, users.get(userid).username, farms.get(farmid).name
                    )) {
                        if (this._db.delete(milkman.id, "milkman")) {
                            Menu.showConfirm("Milkman deleted!");
                        }
                        else {
                            Menu.showError("Milkman not deleted!");
                        }
                    }
                }
            }
        }
        else Menu.showError("Milkman not found!");
    }
}

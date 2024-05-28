package controller;

import model.Farm;
import model.Farms;
import model.Municipalities;
import model.Municipality;
import tools.Database;
import tools.Menu;
import tools.MyTool;
import view.AdminView;
import view.farm.AddFarmView;
import view.farm.DeleteFarmView;
import view.farm.SearchFarmView;
import view.farm.ShowFarmsView;
import view.farm.UpdateFarmView;

public class FarmController {
    Database _db;

    public FarmController(Database _db) {
        this._db = _db;
    }

    public void menu() {
        String[] options = {
            "Show all farms", "Search a farm", "Add a farm", 
            "Update a farm", "Delete a farm"
        };
        int choice;
        do {
            AdminView.showCow();
            Menu.showMenu("Farms menu", options, 2);
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
        Farms farms = new Farms(this._db, "");
        ShowFarmsView.show(farms);
    }

    private void search() {
        String name = MyTool.readName("farm", "name");
        Farms farms = new Farms(this._db, "name LIKE '%" + name + "%'");
        if (farms._farms.length > 0) {
            Farm farm = farms._farms[0];
            Municipalities municipalities = new Municipalities(
                this._db, "id = " + farm.municipalityId
            );
            Municipality municipality = municipalities._municipalities[0];
            SearchFarmView.show(farm, municipality.name);
        }
        else {
            Menu.showError("Farm not found!");
        }
    }

    private void add() {
        Farms farms = new Farms(_db, "");
        Municipalities municipalities = new Municipalities(_db, "");
        Farm farm = AddFarmView.getData(
            farms, municipalities
        );
        if (farm != null) {
            if (this._db.insert(farm)) {
                Menu.showConfirm("Farm added!");
            }
            else {
                Menu.showError("Farm not added!");
            }
        }
    }

    private void update() {
        int id = UpdateFarmView.getId();
        if (id >= 0) {
            Municipalities municipalities = new Municipalities(_db, "");
            Farms farms = new Farms(_db, "id = " + id);
            Farm farm = farms.get(id);
            if (farm != null) {
                farms = new Farms(_db, "");
                farm = UpdateFarmView.getData(farm, farms, municipalities);
                if (farm != null) {
                    if (this._db.update(farm.id, farm)) {
                        Menu.showConfirm("Farm updated!");
                    }
                    else {
                        Menu.showError("Farm not updated!");
                    }
                }
            }
            else {
                Menu.showError("Farm not found!");
            }
        }
    }

    private void delete() {
        int id = DeleteFarmView.getId();
        if (id >= 0) {
            Farms farms = new Farms(_db, "id = " + id);
            Farm farm = farms.get(id);
            if (farm != null) {
                Municipalities municipalities = new Municipalities(
                    _db, "id = " + farm.municipalityId
                );
                Municipality municipality = municipalities.get(farm.municipalityId);
                if (DeleteFarmView.confirm(farm, municipality.name)) {
                    if (this._db.delete(farm.id, "farm")) {
                        Menu.showConfirm("Farm deleted!");
                    }
                    else {
                        Menu.showError("Farm not deleted!");
                    }
                }
            }
            else {
                Menu.showError("Farm not found!");
            }
        }
    }
}

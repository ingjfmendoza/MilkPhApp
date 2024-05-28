package controller;

import model.Department;
import model.Departments;
import model.Municipalities;
import model.Municipality;
import tools.Database;
import tools.Menu;
import tools.MyTool;
import view.AdminView;
import view.municipality.AddMunicipalityView;
import view.municipality.DeleteMunicipalityView;
import view.municipality.SearchMunicipalityView;
import view.municipality.ShowMunicipalitiesView;
import view.municipality.UpdateMunicipalityView;

public class MunicipalityController {
    Database _db;

    public MunicipalityController(Database _db) {
        this._db = _db;
    }

    public void menu() {
        String[] options = {
            "Show all municipalties", "Search a municipality", "Add a municipality", 
            "Update a municipality", "Delete a municipality"
        };
        int choice;
        do {
            AdminView.showCow();
            Menu.showMenu("Municipalities menu", options, 2);
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
        Municipalities municipalities = new Municipalities(this._db, "");
        ShowMunicipalitiesView.show(municipalities);
    }

    private void search() {
        String name = MyTool.readName("municipality", "name");
        Municipalities municipalities = new Municipalities(
            this._db, "name LIKE '%" + name + "%'"
        );
        if (municipalities._municipalities.length > 0) {
            Municipality municipality = municipalities._municipalities[0];
            Departments departments = new Departments(
                this._db, "id = " + municipality.departmentId
            );
            Department department = departments._departments[0];
            SearchMunicipalityView.show(municipality, department.name);
        }
        else {
            Menu.showError("Municipality not found!");
        }
    }

    private void add() {
        Municipalities municipalities = new Municipalities(_db, "");
        Departments departments = new Departments(_db, "");
        Municipality municipality = AddMunicipalityView.getData(
            municipalities, departments
        );
        if (municipality != null) {
            if (this._db.insert(municipality)) {
                Menu.showConfirm("Municipality added!");
            }
            else {
                Menu.showError("Municipality not added!");
            }
        }
    }

    private void update() {
        int id = UpdateMunicipalityView.getId();
        if (id >= 0) {
            Departments departments = new Departments(_db, "");
            Municipalities municipalities = new Municipalities(_db, "id = " + id);
            Municipality municipality = municipalities.get(id);
            if (municipality != null) {
                municipalities = new Municipalities(_db, "");
                municipality = UpdateMunicipalityView.getData(
                    municipality, municipalities, departments
                );
                if (municipality != null) {
                    if (this._db.update(municipality.id, municipality)) {
                        Menu.showConfirm("Municipality updated!");
                    }
                    else {
                        Menu.showError("Municipality not updated!");
                    }
                }
            }
            else {
                Menu.showError("Municipality not found!");
            }
        }
    }

    private void delete() {
        int id = DeleteMunicipalityView.getId();
        if (id >= 0) {
            Municipalities municipalities = new Municipalities(_db, "id = " + id);
            Municipality municipality = municipalities.get(id);
            if (municipality != null) {
                Departments departments = new Departments(
                    _db, "id = " + municipality.departmentId
                );
                Department department = departments.get(municipality.departmentId);
                if (DeleteMunicipalityView.confirm(municipality, department.name)) {
                    if (this._db.delete(municipality.id, "municipality")) {
                        Menu.showConfirm("Municipality deleted!");
                    }
                    else {
                        Menu.showError("Municipality not deleted!");
                    }
                }
            }
            else {
                Menu.showError("Municipality not found!");
            }
        }
    }
}

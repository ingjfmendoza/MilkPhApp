package controller;

import model.Department;
import model.Departments;
import tools.Database;
import tools.Menu;
import tools.MyTool;
import view.AdminView;
import view.department.AddDepartmentView;
import view.department.DeleteDepartmentView;
import view.department.SearchDepartmentView;
import view.department.ShowDepartmentsView;
import view.department.UpdateDepartmentView;

public class DepartmentController {
    Database _db;

    public DepartmentController(Database _db) {
        this._db = _db;
    }

    public void menu() {
        String[] options = {
            "Show all departments", "Search a department", "Add a department", 
            "Update a department", "Delete a department"
        };
        int choice;
        do {
            AdminView.showCow();
            Menu.showMenu("Departments menu", options, 2);
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
        Departments departments = new Departments(this._db, "");
        ShowDepartmentsView.show(departments);
    }

    private void search() {
        String name = MyTool.readName("department", "name");
        Departments departments = new Departments(
            this._db, "name LIKE '%" + name + "%'"
        );
        if (departments._departments.length > 0) {
            SearchDepartmentView.show(departments._departments[0]);
        }
        else {
            Menu.showError("Department not found!");
        }
    }

    private void add() {
        Departments departments = new Departments(_db, "");
        Department department = AddDepartmentView.getData(departments);
        if (department != null) {
            if (this._db.insert(department)) {
                Menu.showConfirm("Department added!");
            }
            else {
                Menu.showError("Department not added!");
            }
        }
    }

    private void update() {
        int id = UpdateDepartmentView.getId();
        if (id >= 0) {
            Departments departments = new Departments(_db, "id = " + id);
            Department department = departments.get(id);
            if (department!= null) {
                departments = new Departments(_db, "");
                department = UpdateDepartmentView.getData(department, departments);
                if (department!= null) {
                    if (this._db.update(department.id, department)) {
                        Menu.showConfirm("Department updated!");
                    }
                    else {
                        Menu.showError("Department not updated!");
                    }
                }
            }
            else {
                Menu.showError("Department not found!");
            }
        }
    }

    private void delete() {
        int id = DeleteDepartmentView.getId();
        if (id >= 0) {
            Departments departments = new Departments(_db, "id = " + id);
            Department department = departments.get(id);
            if (department!= null) {
                if (DeleteDepartmentView.confirm(department)) {
                    if (this._db.delete(department.id, "department")) {
                        Menu.showConfirm("Department deleted!");
                    }
                    else {
                        Menu.showError("Department not deleted!");
                    }
                }
            }
            else {
                Menu.showError("Department not found!");
            }
        }
    }
}

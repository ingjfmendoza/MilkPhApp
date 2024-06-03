package controller;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
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

    public void fillTable(TableView<Department> tbvDepartments) {
        Departments departments = new Departments(_db, "");

        if (tbvDepartments.getColumns().size() == 0) {
            createTableColumns(tbvDepartments);
        }

        tbvDepartments.getItems().clear();
        for (Department department : departments._departments) {
            tbvDepartments.getItems().add(department);
        }
        tbvDepartments.refresh();
    }

    private void createTableColumns(TableView<Department> tbvDepartments) {
        TableColumn<Department, Integer> colId = new TableColumn<Department, Integer>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<Department, Integer>("id"));
        tbvDepartments.getColumns().add(colId);

        TableColumn<Department, Integer> colDANE = new TableColumn<Department, Integer>("DANE");
        colDANE.setCellValueFactory(new PropertyValueFactory<Department, Integer>("DANEId"));
        tbvDepartments.getColumns().add(colDANE);

        TableColumn<Department, String> colName = new TableColumn<Department, String>("Name");
        colName.setCellValueFactory(new PropertyValueFactory<Department, String>("name"));
        tbvDepartments.getColumns().add(colName);
    }

    public void add(String DANE, String name, TableView<Department> tbvDepartments) {
        boolean ok = name.length() > 0;
        ok = ok && DANE.length() > 0;
        int DANEId = 0;
        try {
            DANEId = Integer.parseInt(DANE);
        } catch (Exception e) {
            ok = false;
        }

        if (ok) {
            Alert a = new Alert(AlertType.CONFIRMATION);
            a.setContentText("Please confirm the add operation.");
            Optional<ButtonType> choice = a.showAndWait();

            if (choice.get() == ButtonType.OK) {
                Department department = new Department(DANEId, name);
                if (this._db.insert(department)) {
                    fillTable(tbvDepartments);

                    a = new Alert(AlertType.INFORMATION);
                    a.setContentText("Department added!");
                    a.show();
                }
                else {
                    a = new Alert(AlertType.ERROR);
                    a.setContentText("Department not added!");
                    a.show();
                }
            }
        }
        else {
            Alert a = new Alert(AlertType.ERROR);
            a.setContentText("Some fields to add are empty or wrong. Please check them.");
            a.show();
        }
    }

    public void update(String id, String DANE, String name, TableView<Department> tbvDepartments) {
        boolean ok = name.length() > 0;
        ok = ok && DANE.length() > 0;
        int DANEId = 0;
        int departmentId = 0;
        try {
            departmentId = Integer.parseInt(id);
            DANEId = Integer.parseInt(DANE);
        } catch (Exception e) {
            ok = false;
        }

        if (ok) {
            Alert a = new Alert(AlertType.CONFIRMATION);
            a.setContentText("Please confirm the update operation.");
            Optional<ButtonType> choice = a.showAndWait();

            if (choice.get() == ButtonType.OK) {
                Department department = new Department(DANEId, name);
                if (this._db.update(departmentId, department)) {
                    fillTable(tbvDepartments);

                    a = new Alert(AlertType.INFORMATION);
                    a.setContentText("Department updated!");
                    a.show();
                }
                else {
                    a = new Alert(AlertType.ERROR);
                    a.setContentText("Department not updated!");
                    a.show();
                }
            }

        }
        else {
            Alert a = new Alert(AlertType.ERROR);
            a.setContentText("Some fields to add are empty or wrong. Please check them.");
            a.show();
        }
    }

    public boolean delete(String id, TableView<Department> tbvDepartments) {
        int departmentId;
        try {
            departmentId = Integer.parseInt(id);
        } catch (Exception e) {
            return false;
        }

        Alert a = new Alert(AlertType.CONFIRMATION);
        a.setContentText("Please confirm the delete operation.");
        Optional<ButtonType> choice = a.showAndWait();

        if (choice.get() == ButtonType.OK) {
            if (this._db.delete(departmentId, "department")) {
                fillTable(tbvDepartments);

                a = new Alert(AlertType.INFORMATION);
                a.setContentText("Department deleted!");
                a.show();
                return true;
            }
            else {
                a = new Alert(AlertType.ERROR);
                a.setContentText("Department not delete!");
                a.show();
            }
        }
        return false;
    }
}

package view.department;

import model.Department;
import model.Departments;
import tools.Menu;
import tools.MyTool;

public class UpdateDepartmentView {

    public static int getId() {
        MyTool.header("Update Department");
        return MyTool.readInt("department Id", -1);
    }

    public static Department getData(Department department, Departments departments) {
        int choice;
        do {
            Menu.showMenu(" Update " + department.name, department.options(), 2);
            choice = Menu.getOption(department.options());
            switch (choice) {
                case 1: department.readDANEId(); break;
                case 2: department.readName(); break;
            }

        } while (choice != 0);

        Department d = departments.get(department.name);
        boolean found = false;
        if (d != null && d.id != department.id) found = true;
        else {
            d = departments.getDANEId(department.DANEId);
            if (d != null && d.id != department.id) found = true;
        }

        if (found) {
            Menu.showError("Department already exists!");
            return null;
        }
        else return department;
    }
}

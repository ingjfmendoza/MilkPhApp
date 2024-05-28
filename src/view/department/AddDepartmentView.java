package view.department;

import model.Department;
import model.Departments;
import tools.Menu;
import tools.MyTool;

public class AddDepartmentView {

    public static Department getData(Departments departments) {
        MyTool.header("Add Department");
        Department department = new Department();
        department.readName();
        if (department.name == null) return null;
        if (departments.exists(department.name)) {
            Menu.showError("Department already exists!");
            return null;
        }
        department.readDANEId();
        return department;
    }

}

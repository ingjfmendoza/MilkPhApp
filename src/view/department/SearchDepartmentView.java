package view.department;

import model.Department;
import tools.Menu;

public class SearchDepartmentView {

    public static void show(Department department) {
        System.out.println(department.details());
        Menu.anyKey();
    }

}

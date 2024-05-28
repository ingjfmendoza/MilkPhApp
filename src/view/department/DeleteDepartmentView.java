package view.department;

import model.Department;
import tools.Menu;
import tools.MyTool;

public class DeleteDepartmentView {

    public static int getId() {
        MyTool.header("Delete Department");
        return MyTool.readInt("department Id", -1);
    }

    public static boolean confirm(Department department) {
        System.out.println(department.details());
        if (Menu.getConfirm(
            "Are you sure you want to delete this department?"
        )) return true;
        return false;
    }

}

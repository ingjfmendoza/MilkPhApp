package view.department;

import model.Department;
import model.Departments;
import tools.Menu;
import tools.MyTool;

public class ShowDepartmentsView {

    public static void show(Departments departments) {
        MyTool.header("List of Departments");
        if (departments._departments.length > 0) {
            departments.updateWidths();
            System.out.print(departments.header());
            for (Department department : departments._departments) {
                System.out.print(department.tabular(departments._widths));
            }
            System.out.println(departments._line);
        }
        Menu.anyKey();
    }

}

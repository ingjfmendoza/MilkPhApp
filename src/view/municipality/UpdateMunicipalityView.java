package view.municipality;

import model.Department;
import model.Departments;
import model.Municipalities;
import model.Municipality;
import tools.Menu;
import tools.MyTool;

public class UpdateMunicipalityView {

    public static int getId() {
        MyTool.header("Update Municipality");
        return MyTool.readInt("municipality Id", -1);
    }

    public static Municipality getData(Municipality municipality, Municipalities municipalities, Departments departments) {
        int choice;
        do {
            Department department = departments.get(municipality.departmentId);
            if (department == null) {
                Menu.showError("Department not found!");
                return null;
            }
            Menu.showMenu(
                " Update " + municipality.name, 
                municipality.options(department.name), 
                2
            );
            choice = Menu.getOption(municipality.options(department.name));
            switch (choice) {
                case 1: municipality.readDANEId(); break;
                case 2: municipality.readName(); break;
                case 3: municipality.readDepartmentId(); break;
                case 4: municipality.readLatitude(); break;
                case 5: municipality.readLongitude(); break;
            }
        } while (choice != 0);

        Municipality m = municipalities.get(municipality.name, municipality.departmentId);
        boolean found = false;
        if (m != null && m.id != municipality.id) found = true;
        else {
            m = municipalities.getDANEId(municipality.DANEId);
            if (m != null && m.id != municipality.id) found = true;
        }

        if (found) {
            Menu.showError("Municipality already exists!");
            return null;
        }
        else return municipality;
    }
}

package view.municipality;

import model.Department;
import model.Departments;
import model.Municipalities;
import model.Municipality;
import tools.Menu;
import tools.MyTool;

public class AddMunicipalityView {

    public static Municipality getData(Municipalities municipalities, Departments departments) {
        MyTool.header("Add Municipality");
        Municipality municipality = new Municipality();
        municipality.readName();
        if (municipality.name == null) return null;
        
        Department department;
        do {
            municipality.readDepartmentId();
            department = departments.get(municipality.departmentId);
        } while (department == null);
        
        if (municipalities.exists(municipality.name, municipality.departmentId)) {
            Menu.showError("Municipality already exists!");
            return null;
        }
        municipality.readDANEId();
        municipality.readLatitude();
        municipality.readLongitude();
        return municipality;
    }

}

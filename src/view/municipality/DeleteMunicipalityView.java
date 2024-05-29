package view.municipality;

import model.Municipality;
import tools.Menu;
import tools.MyTool;

public class DeleteMunicipalityView {

    public static int getId() {
        MyTool.header("Delete Municipality");
        return MyTool.readInt("municipality id", -1);
    }

    public static boolean confirm(Municipality municipality, String departmentName) {
        System.out.println(municipality.details(departmentName));
        if (Menu.getConfirm(
            "Are you sure you want to delete this municipality?"
        )) return true;
        return false;
    }

}

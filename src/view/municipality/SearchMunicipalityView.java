package view.municipality;

import model.Municipality;
import tools.Menu;

public class SearchMunicipalityView {

    public static void show(Municipality municipality, String departmentName) {
        System.out.println(municipality.details(departmentName));
        Menu.anyKey();
    }

}

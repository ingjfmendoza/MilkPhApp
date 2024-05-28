package view.farm;

import model.Farm;
import tools.Menu;

public class SearchFarmView {

    public static void show(Farm farm, String municipalityName) {
        System.out.println(farm.details(municipalityName));
        Menu.anyKey();
    }

}

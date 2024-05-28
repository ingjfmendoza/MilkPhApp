package view.farm;

import model.Farm;
import tools.Menu;
import tools.MyTool;

public class DeleteFarmView {

    public static int getId() {
        MyTool.header("Delete Farm");
        return MyTool.readInt("farm id", -1);
    }

    public static boolean confirm(Farm farm, String municipalitytName) {
        System.out.println(farm.details(municipalitytName));
        if (Menu.getConfirm(
            "Are you sure you want to delete this farm?"
        )) return true;
        return false;
    }

}

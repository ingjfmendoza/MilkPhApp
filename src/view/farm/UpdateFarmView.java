package view.farm;

import model.Farm;
import model.Farms;
import model.Municipalities;
import model.Municipality;
import tools.Menu;
import tools.MyTool;

public class UpdateFarmView {

    public static int getId() {
        MyTool.header("Update Farm");
        return MyTool.readInt("farm Id", -1);
    }

    public static Farm getData(Farm farm, Farms farms, Municipalities municipalities) {
        int choice;
        do {
            Municipality municipality = municipalities.get(farm.municipalityId);
            if (municipality == null) {
                Menu.showError("Municipality not found!");
                return null;
            }
            Menu.showMenu(
                " Update " + farm.name, 
                farm.options(municipality.name), 
                2
            );
            choice = Menu.getOption(farm.options(municipality.name));
            switch (choice) {
                case 1: farm.readName(); break;
                case 2: farm.readOwnerName(); break;
                case 3: farm.readAddress(); break;
                case 4: farm.readPhone(); break;
                case 5: farm.readMunicipalityId(); break;
            }
        } while (choice != 0);

        Farm f = farms.get(farm.name, farm.municipalityId);
        boolean found = false;
        if (f != null && f.id != farm.id) found = true;
        if (found) {
            Menu.showError("Farm already exists!");
            return null;
        }
        else return farm;
    }
}

package view.farm;

import model.Farm;
import model.Farms;
import model.Municipalities;
import model.Municipality;
import tools.Menu;
import tools.MyTool;

public class AddFarmView {

    public static Farm getData(Farms farms, Municipalities municipalities) {
        MyTool.header("Add Farm");
        Farm farm = new Farm();
        farm.readName();
        if (farm.name == null) return null;
        
        Municipality municipality;
        do {
            farm.readMunicipalityId();
            municipality = municipalities.get(farm.municipalityId);
        } while (municipality == null);
        
        if (farms.exists(farm.name, farm.municipalityId)) {
            Menu.showError("Farm already exists!");
            return null;
        }
        farm.readOwnerName();
        farm.readAddress();
        farm.readPhone();
        return farm;
    }

}

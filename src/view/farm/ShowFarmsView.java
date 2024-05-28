package view.farm;

import model.Farm;
import model.Farms;
import tools.Menu;
import tools.MyTool;

public class ShowFarmsView {

    public static void show(Farms farms) {
        MyTool.header("List of Farms");
        if (farms._farms.length > 0) {
            farms.updateWidths();
            System.out.print(farms.header());
            for (Farm farm : farms._farms) {
                System.out.print(farm.tabular(farms._widths));
            }
            System.out.println(farms._line);
        }
        Menu.anyKey();
    }

}

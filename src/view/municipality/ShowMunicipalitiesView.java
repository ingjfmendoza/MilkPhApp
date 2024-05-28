package view.municipality;

import model.Municipalities;
import model.Municipality;
import tools.Menu;
import tools.MyTool;

public class ShowMunicipalitiesView {

    public static void show(Municipalities municipalities) {
        MyTool.header("List of Municipalities");
        if (municipalities._municipalities.length > 0) {
            municipalities.updateWidths();
            System.out.print(municipalities.header());
            for (Municipality municipality : municipalities._municipalities) {
                System.out.print(municipality.tabular(municipalities._widths));
            }
            System.out.println(municipalities._line);
        }
        Menu.anyKey();
    }

}

package view.milkman;

import model.Milkman;
import model.Milkmen;
import tools.Menu;
import tools.MyTool;

public class ShowMilkmenView {

    public static void show(Milkmen milkmen) {
        MyTool.header("List of Milkmen");
        if (milkmen._milkmen.length > 0) {
            milkmen.updateWidths();
            System.out.print(milkmen.header());
            for (Milkman milkman : milkmen._milkmen) {
                System.out.print(milkman.tabular(milkmen._widths));
            }
            System.out.println(milkmen._line);
        }
        Menu.anyKey();
    }

}

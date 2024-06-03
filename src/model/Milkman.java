package model;

import tools.Menu;
import tools.MyTool;

public class Milkman {
    public int id;
    public int userId;
    public int farmId;

    public Milkman() {
    }

    public Milkman(int userId, int farmId) {
        this.userId = userId;
        this.farmId = farmId;
    }

    public Milkman(String[] data) {
        this.id     = Integer.parseInt(data[0]);
        this.userId = Integer.parseInt(data[1]);
        this.farmId = Integer.parseInt(data[2]);
    }
    
    public void readUserId() {
        do {
            this.userId = MyTool.readInt("User Id", -1);
            if (this.userId >= 0) break;
            Menu.showError("User id must be greater or equal than 0!");
        } while (true);
    }

    public void readFarmId() {
        do {
            this.farmId = MyTool.readInt("Farm Id", -1);
            if (this.farmId >= 0) break;
            Menu.showError("Farm id must be greater or equal than 0!");
        } while (true);
    }

    public String details(String username, String farmName) {
        String s = "";
        s += "Id      : " + this.id + "\n";
        s += "User Id : " + this.userId + " (" + username + ")" + "\n";
        s += "Farm Id : " + this.farmId + " (" + farmName + ")" + "\n";
        return s;
    }

    public String tabular(int[] widths) {
        String s = "";
        s += "|" + Menu.align("" + this.id, widths[0], Menu.RIGHT);
        s += "|" + Menu.align("" + this.userId, widths[1], Menu.RIGHT);
        s += "|" + Menu.align("" + this.farmId, widths[2], Menu.RIGHT);
        return s + "|\n";
    }

    public String[] options(String username, String farmName) {
        String[] options = {
            "User : " + this.userId + " (" + username + ")",
            "Farm : " + this.farmId + " (" + farmName + ")",
        };
        return options;
    }
}

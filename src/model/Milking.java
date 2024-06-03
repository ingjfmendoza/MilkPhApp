package model;

import tools.Menu;
import tools.MyTool;

public class Milking {
    public int  id;
    public int  userId;
    public int  farmId;
    public long milkingDate;
    public int  cowNumbers;
    public int  liters;

    public Milking() {
    }

    public Milking(int userId, int farmId) {
        this.userId = userId;
        this.farmId = farmId;
    }

    public Milking(String[] data) {
        this.id          = Integer.parseInt(data[0]);
        this.userId      = Integer.parseInt(data[1]);
        this.farmId      = Integer.parseInt(data[2]);
        this.milkingDate = Long.parseLong(data[3]);
        this.cowNumbers  = Integer.parseInt(data[4]);
        this.liters      = Integer.parseInt(data[5]);
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

    public void readMilkingDate() {
        this.milkingDate = MyTool.readDate();
    }

    public void readCowsNumber() {
        do {
            this.cowNumbers = MyTool.readInt("Cows number", 0);
            if (this.cowNumbers >= 0) break;
            Menu.showError("Cows number must be greater than 0!");
        } while (true);
    }

    public void readLiters() {
        do {
            this.liters = MyTool.readInt("Liters of milk", 0);
            if (this.liters >= 0) break;
            Menu.showError("Liters of milk must be greater than 0!");
        } while (true);
    }

    public String details(String username, String farmName) {
        String s = "";
        s += "Id             : " + this.id + "\n";
        s += "User           : " + this.userId + " (" + username + ")" + "\n";
        s += "Farm           : " + this.farmId + " (" + farmName + ")" + "\n";
        s += "Date           : " + MyTool.getDate(this.milkingDate) + "\n";
        s += "Cows           : " + this.cowNumbers + "\n";
        s += "Liters of milk : " + this.liters + "\n";
        return s;
    }

    public String tabular(int[] widths) {
        String s = "";
        s += "|" + Menu.align("" + this.id, widths[0], Menu.RIGHT);
        s += "|" + Menu.align("" + this.userId, widths[1], Menu.RIGHT);
        s += "|" + Menu.align("" + this.farmId, widths[2], Menu.RIGHT);
        s += "|" + Menu.align(MyTool.getDate(this.milkingDate), widths[3], Menu.LEFT);
        s += "|" + Menu.align("" + this.cowNumbers, widths[4], Menu.RIGHT);
        s += "|" + Menu.align("" + this.liters, widths[5], Menu.RIGHT);
        return s + "|\n";
    }

    public String[] options(String username, String farmName) {
        String[] options = {
            "User           : " + this.userId + " (" + username + ")",
            "Farm           : " + this.farmId + " (" + farmName + ")",
            "Date           : " + MyTool.getDate(this.milkingDate),
            "Cows           : " + this.cowNumbers,
            "Liters of milk : " + this.liters,
        };
        return options;
    }
}

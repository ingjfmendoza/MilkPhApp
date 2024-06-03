package model;

import tools.DataTableView;
import tools.Database;
import tools.Menu;
import tools.MyTool;

public class Milkings {
    Database         _db;
    public Milking[] _milkings;
    public int[]     _widths;
    final  String[]  _headers = {"Id", "User", "Farm", "Date", "Cows", "Liters"};
    public String    _line;

    public Milkings(Database db, String filter) {
        this._db = db;
        select(filter);
    }

    private void select(String filter) {
        String[][] data = this._db.select("milking", filter);
        this._milkings = new Milking[data.length - 1];
        for (int i = 1; i < data.length; i++) {
            this._milkings[i - 1] = new Milking(data[i]);
        }
        this._widths = MyTool.initWiths(this._headers);
    }

    public Milking get(int id) {
        for (Milking milking : this._milkings) {
            if (milking.id == id) return milking;
        }
        return null;
    }

    public Milking get(int userId, int farmId, long date) {
        for (Milking milking : this._milkings) {
            if (
                milking.userId == userId && 
                milking.farmId == farmId &&
                milking.milkingDate == date
            ) 
                return milking;
        }
        return null;
    }

    public boolean exists(int userId, int farmId, long date) {
        for (Milking milking : this._milkings) {
            if (
                milking.userId == userId && 
                milking.farmId == farmId &&
                milking.milkingDate == date
            ) 
                return true;
        }
        return false;
    }

    public String[] tabular() {
        String[] data = new String[this._milkings.length];
        for (int i = 0; i < this._milkings.length; i++) {
            data[i] = this._milkings[i].tabular(this._widths);
        }
        return data;
    }

    public void updateWidths() {
        this._widths = MyTool.initWiths(this._headers);
        for (Milking milking : this._milkings) {
            if (this._widths[0] < ("" + milking.id).length()) {
                this._widths[0] = ("" + milking.id).length();
            }
            if (this._widths[1] < ("" + milking.userId).length()) {
                this._widths[1] = ("" + milking.userId).length();
            }
            if (this._widths[2] < ("" + milking.farmId).length()) {
                this._widths[2] = ("" + milking.farmId).length();
            }
            if (this._widths[3] < MyTool.getDate(milking.milkingDate).length()) {
                this._widths[3] = MyTool.getDate(milking.milkingDate).length();
            }
            if (this._widths[4] < ("" + milking.cowNumbers).length()) {
                this._widths[4] = ("" + milking.cowNumbers).length();
            }
            if (this._widths[5] < ("" + milking.liters).length()) {
                this._widths[5] = ("" + milking.liters).length();
            }
        }
    }

    public String header() {
        this._line = DataTableView.getHorizontalLine(this._widths);
        String s = this._line + "\n";
        for (int i = 0; i < this._headers.length; i++) {
            s += "|" + Menu.align(this._headers[i], this._widths[i], Menu.CENTER);
        }
        return s + "|\n" + this._line + "\n";
    }

    public String[] options(Users users, Farms farms) {
        String[] options = new String[this._milkings.length];
        for (int i = 0; i < this._milkings.length; i++) {
            options[i] = users.get(this._milkings[i].userId).username + 
                " - " + farms.get(this._milkings[i].farmId).name +
                " - " + MyTool.getDate(this._milkings[i].milkingDate);
        }
        return options;
    }
}

package model;

import tools.DataTableView;
import tools.Database;
import tools.Menu;
import tools.MyTool;

public class Milkmen {
    Database         _db;
    public Milkman[] _milkmen;
    public int[]     _widths;
    final String[]   _headers = {"Id", "User", "Farm"};
    public String    _line;

    public Milkmen(Database db, String filter) {
        this._db = db;
        select(filter);
    }

    private void select(String filter) {
        String[][] data = this._db.select("milkman", filter);
        this._milkmen = new Milkman[data.length - 1];
        for (int i = 1; i < data.length; i++) {
            this._milkmen[i - 1] = new Milkman(data[i]);
        }
        this._widths = MyTool.initWiths(this._headers);
    }

    public Milkman get(int id) {
        for (Milkman milkman : this._milkmen) {
            if (milkman.id == id) return milkman;
        }
        return null;
    }

    public Milkman get(int userId, int farmId) {
        for (Milkman milkman : this._milkmen) {
            if (milkman.userId == userId && milkman.farmId == farmId) 
                return milkman;
        }
        return null;
    }

    public boolean exists(int userId, int farmId) {
        for (Milkman milkman : this._milkmen) {
            if (milkman.userId == userId && milkman.farmId == farmId) 
                return true;
        }
        return false;
    }

    public String[] tabular() {
        String[] data = new String[this._milkmen.length];
        for (int i = 0; i < this._milkmen.length; i++) {
            data[i] = this._milkmen[i].tabular(this._widths);
        }
        return data;
    }

    public void updateWidths() {
        this._widths = MyTool.initWiths(this._headers);
        for (Milkman milkman : this._milkmen) {
            if (this._widths[0] < ("" + milkman.id).length()) {
                this._widths[0] = ("" + milkman.id).length();
            }
            if (this._widths[1] < ("" + milkman.userId).length()) {
                this._widths[1] = ("" + milkman.userId).length();
            }
            if (this._widths[2] < ("" + milkman.farmId).length()) {
                this._widths[2] = ("" + milkman.farmId).length();
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
        String[] options = new String[this._milkmen.length];
        for (int i = 0; i < this._milkmen.length; i++) {
            options[i] = users.get(this._milkmen[i].userId).username + 
                " - " +  farms.get(this._milkmen[i].farmId).name;
        }
        return options;
    }
}

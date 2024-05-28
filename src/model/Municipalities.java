package model;

import tools.DataTableView;
import tools.Database;
import tools.Menu;
import tools.MyTool;

public class Municipalities {
    Database              _db;
    public Municipality[] _municipalities;
    public int[]          _widths;
    final String[]        _headers = {"Id", "DANE Id", "Name", "Department", "Latitude", "Longitude"};
    public String         _line;

    public Municipalities(Database db, String filter) {
        this._db = db;
        select(filter);
    }

    private void select(String filter) {
        String[][] data = this._db.select("municipality", filter);
        this._municipalities = new Municipality[data.length - 1];
        for (int i = 1; i < data.length; i++) {
            this._municipalities[i - 1] = new Municipality(data[i]);
        }
        this._widths = MyTool.initWiths(this._headers);
    }

    public Municipality get(int id) {
        for (Municipality municipality : this._municipalities) {
            if (municipality.id == id) return municipality;
        }
        return null;
    }

    public Municipality get(String name, int departmentId) {
        for (Municipality municipality : this._municipalities) {
            if (
                municipality.name.equalsIgnoreCase(name) && 
                municipality.departmentId == departmentId
            ) return municipality;
        }
        return null;
    }

    public Municipality get(String name) {
        for (Municipality municipality : this._municipalities) {
            if (municipality.name.equalsIgnoreCase(name)) 
                return municipality;
        }
        return null;
    }

    public Municipality getDANEId(int DANEId) {
        for (Municipality municipality : this._municipalities) {
            if (municipality.DANEId == DANEId) return municipality;
        }
        return null;
    }

    public boolean exists(String name, int departmentId) {
        for (Municipality municipality : this._municipalities) {
            if (municipality.name.equals(name) && 
                municipality.departmentId == departmentId
            ) return true;
        }
        return false;
    }

    public String[] tabular() {
        String[] data = new String[this._municipalities.length];
        for (int i = 0; i < this._municipalities.length; i++) {
            data[i] = this._municipalities[i].tabular(this._widths);
        }
        return data;
    }

    public void updateWidths() {
        this._widths = MyTool.initWiths(this._headers);
        for (Municipality municipality : this._municipalities) {
            if (this._widths[0] < ("" + municipality.id).length()) {
                this._widths[0] = ("" + municipality.id).length();
            }
            if (this._widths[1] < ("" + municipality.DANEId).length()) {
                this._widths[1] = ("" + municipality.DANEId).length();
            }
            if (this._widths[2] < municipality.name.length()) {
                this._widths[2] = municipality.name.length();
            }
            if (this._widths[3] < ("" + municipality.departmentId).length()) {
                this._widths[3] = ("" + municipality.departmentId).length();
            }
            if (this._widths[4] < ("" + municipality.latitude).length()) {
                this._widths[4] = ("" + municipality.latitude).length();
            }
            if (this._widths[5] < ("" + municipality.longitude).length()) {
                this._widths[5] = ("" + municipality.longitude).length();
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
}

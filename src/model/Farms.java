package model;

import tools.DataTableView;
import tools.Database;
import tools.Menu;
import tools.MyTool;

public class Farms {
    Database       _db;
    public Farm[]  _farms;
    public int[]   _widths;
    final String[] _headers = {"Id", "Name", "Owner name", "Address", "Phone", "Municipality"};
    public String  _line;

    public Farms(Database db, String filter) {
        this._db = db;
        select(filter);
    }

    private void select(String filter) {
        String[][] data = this._db.select("farm", filter);
        this._farms = new Farm[data.length - 1];
        for (int i = 1; i < data.length; i++) {
            this._farms[i - 1] = new Farm(data[i]);
        }
        this._widths = MyTool.initWiths(this._headers);
    }

    public Farm get(int id) {
        for (Farm farm : this._farms) {
            if (farm.id == id) return farm;
        }
        return null;
    }

    public Farm get(String name, int municipalityId) {
        for (Farm farm : this._farms) {
            if (
                farm.name.equalsIgnoreCase(name) && 
                farm.municipalityId == municipalityId
            ) return farm;
        }
        return null;
    }

    public Farm get(String name) {
        for (Farm farm : this._farms) {
            if (farm.name.equalsIgnoreCase(name)) 
                return farm;
        }
        return null;
    }

    public boolean exists(String name, int municipalityId) {
        for (Farm farm : this._farms) {
            if (farm.name.equals(name) && 
                farm.municipalityId == municipalityId
            ) return true;
        }
        return false;
    }

    public String[] tabular() {
        String[] data = new String[this._farms.length];
        for (int i = 0; i < this._farms.length; i++) {
            data[i] = this._farms[i].tabular(this._widths);
        }
        return data;
    }

    public void updateWidths() {
        this._widths = MyTool.initWiths(this._headers);
        for (Farm farm : this._farms) {
            if (this._widths[0] < ("" + farm.id).length()) {
                this._widths[0] = ("" + farm.id).length();
            }
            if (this._widths[1] < farm.name.length()) {
                this._widths[1] = farm.name.length();
            }
            if (this._widths[2] < farm.ownerName.length()) {
                this._widths[2] = farm.ownerName.length();
            }
            if (this._widths[3] < farm.address.length()) {
                this._widths[3] = farm.address.length();
            }
            if (this._widths[4] < farm.phone.length()) {
                this._widths[4] = farm.phone.length();
            }
            if (this._widths[5] < ("" + farm.municipalityId).length()) {
                this._widths[5] = ("" + farm.municipalityId).length();
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

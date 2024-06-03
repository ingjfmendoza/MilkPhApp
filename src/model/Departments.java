package model;

import tools.DataTableView;
import tools.Database;
import tools.Menu;
import tools.MyTool;

public class Departments {
    Database            _db;
    public Department[] _departments;
    public int[]        _widths;
    final String[]      _headers = {"Id", "DANE Id", "Name"};
    public String       _line;

    public Departments(Database db, String filter) {
        this._db = db;
        select(filter);
    }

    private void select(String filter) {
        String[][] data = this._db.select("department", filter);
        this._departments = new Department[data.length - 1];
        for (int i = 1; i < data.length; i++) {
            this._departments[i - 1] = new Department(data[i]);
        }
        this._widths = MyTool.initWiths(this._headers);
    }

    public Department get(int id) {
        for (Department department : this._departments) {
            if (department.id == id) return department;
        }
        return null;
    }

    public Department get(String name) {
        for (Department department : this._departments) {
            if (department.name.equalsIgnoreCase(name)) 
                return department;
        }
        return null;
    }

    public Department getDANEId(int DANEId) {
        for (Department department : this._departments) {
            if (department.DANEId == DANEId) return department;
        }
        return null;
    }

    public boolean exists(String name) {
        for (Department department : this._departments) {
            if (department.name.equals(name)) return true;
        }
        return false;
    }

    public String[] tabular() {
        String[] data = new String[this._departments.length];
        for (int i = 0; i < this._departments.length; i++) {
            data[i] = this._departments[i].tabular(this._widths);
        }
        return data;
    }

    public void updateWidths() {
        this._widths = MyTool.initWiths(this._headers);
        for (Department department : this._departments) {
            if (this._widths[0] < ("" + department.id).length()) {
                this._widths[0] = ("" + department.id).length();
            }
            if (this._widths[1] < ("" + department.DANEId).length()) {
                this._widths[1] = ("" + department.DANEId).length();
            }
            if (this._widths[2] < department.name.length()) {
                this._widths[2] = department.name.length();
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

    public String[] options() {
        String[] options = new String[this._departments.length];
        for (int i = 0; i < this._departments.length; i++) {
            options[i] = this._departments[i].name;
        }
        return options;
    }
}

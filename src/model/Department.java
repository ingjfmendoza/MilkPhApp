package model;

import tools.Menu;
import tools.MyTool;

public class Department {
    public int id;
    public int DANEId;
    public String name;

    public Department() {
    }

    public Department(int DANEId, String name) {
        this.DANEId = DANEId;
        this.name   = name;
    }
    
    public Department(String[] data) {
        this.id     = Integer.parseInt(data[0]);
        this.DANEId = Integer.parseInt(data[1]);
        this.name   = data[2];
    }
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDANEId() {
        return DANEId;
    }

    public void setDANEId(int dANEId) {
        DANEId = dANEId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void readDANEId() {
        do {
            this.DANEId = MyTool.readInt("DANE Id", 0);
            if (this.DANEId > 0) break;
            Menu.showError("DANE Id must be greater than 0!");
        } while (true);
    }

    public void readName() {
        this.name = MyTool.readName();
        if (this.name.length() <= 0) this.name = null;
    }

    public String details() {
        String s = "";
        s += "Id      : " + this.id + "\n";
        s += "DANE Id : " + this.DANEId + "\n";
        s += "Name    : " + this.name + "\n";
        return s;
    }

    public String tabular(int[] widths) {
        String s = "";
        s += "|" + Menu.align("" + this.id, widths[0], Menu.RIGHT);
        s += "|" + Menu.align("" + this.DANEId, widths[1], Menu.RIGHT);
        s += "|" + Menu.align("" + this.name, widths[2], Menu.LEFT);
        return s + "|\n";
    }

    public String[] options() {
        String[] options = {
            "DANE Id: " + this.DANEId,
            "Name   : " + this.name,
        };
        return options;
    }
}

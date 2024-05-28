package model;

import tools.Menu;
import tools.MyTool;

public class Municipality {
    public int id;
    public int DANEId;
    public String name;
    public int departmentId;
    public float latitude;
    public float longitude;

    public Municipality() {
    }

    public Municipality(String[] data) {
        this.id           = Integer.parseInt(data[0]);
        this.DANEId       = Integer.parseInt(data[1]);
        this.name         = data[2];
        this.departmentId = Integer.parseInt(data[3]);
        this.latitude     = Float.parseFloat(data[4]);
        this.longitude    = Float.parseFloat(data[5]);
    }
    
    public void readDANEId() {
        do {
            this.DANEId = MyTool.readInt("DANE Id", 0);
            if (this.DANEId > 0) break;
            Menu.showError("DANE Id must be grater than 0!");
        } while (true);
    }

    public void readName() {
        this.name = MyTool.readName();
        if (this.name.length() <= 0) this.name = null;
    }

    public void readDepartmentId() {
        do {
            this.departmentId = MyTool.readInt("Department Id", -1);
            if (this.departmentId >= 0) break;
            Menu.showError("Department id must be greater or equal than 0!");
        } while (true);
    }

    public void readLatitude() {
        do {
            this.latitude = MyTool.readFloat("Latitude", -90f);
            if (this.latitude >= -90f && this.latitude <= 90) break;
            Menu.showError("Latitude must be in the range [-90 to 90]!");
        } while (true);
    }

    public void readLongitude() {
        do {
            this.longitude = MyTool.readFloat("Longitude", -180f);
            if (this.longitude >= -180f && this.longitude <= 180) break;
            Menu.showError("Longitude must be in the range [-180 to 180]!");
        } while (true);
    }

    public String details(String departmentName) {
        String s = "";
        s += "Id            : " + this.id + "\n";
        s += "DANE Id       : " + this.DANEId + "\n";
        s += "Name          : " + this.name + "\n";
        s += "Department Id : " + this.departmentId + " (" + departmentName + ")" + "\n";
        s += "Latitude      : " + this.latitude + "\n";
        s += "Longitude     : " + this.longitude + "\n";
        return s;
    }

    public String tabular(int[] widths) {
        String s = "";
        s += "|" + Menu.align("" + this.id, widths[0], Menu.RIGHT);
        s += "|" + Menu.align("" + this.DANEId, widths[1], Menu.RIGHT);
        s += "|" + Menu.align("" + this.name, widths[2], Menu.LEFT);
        s += "|" + Menu.align("" + this.departmentId, widths[3], Menu.RIGHT);
        s += "|" + Menu.align("" + this.latitude, widths[4], Menu.RIGHT);
        s += "|" + Menu.align("" + this.longitude, widths[5], Menu.RIGHT);
        return s + "|\n";
    }

    public String[] options(String departmentName) {
        String[] options = {
            "DANE Id    : " + this.DANEId,
            "Name       : " + this.name,
            "Department : " + this.departmentId + " (" + departmentName + ")",
            "Latitude   : " + this.latitude,
            "Longitude  : " + this.longitude,
        };
        return options;
    }
}

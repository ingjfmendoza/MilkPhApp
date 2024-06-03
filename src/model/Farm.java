package model;

import tools.Menu;
import tools.MyTool;

public class Farm {
    public int id;
    public String name;
    public String ownerName;
    public String address;
    public String phone;
    public int municipalityId;

    public Farm() {
    }

    public Farm(String[] data) {
        this.id             = Integer.parseInt(data[0]);
        this.name           = data[1];
        this.ownerName      = data[2];
        this.address        = data[3];
        this.phone          = data[4];
        this.municipalityId = Integer.parseInt(data[5]);
    }

    public void readName() {
        this.name = MyTool.readName();
        if (this.name.length() <= 0) this.name = null;
    }

    public void readOwnerName() {
        this.ownerName = MyTool.readString("owner name");
    }

    public void readAddress() {
        this.address = MyTool.readString("address");
    }

    public void readPhone() {
        this.phone = MyTool.readString("phone");
    }

    public void readMunicipalityId() {
        do {
            this.municipalityId = MyTool.readInt("Municipality Id", -1);
            if (this.municipalityId >= 0) break;
            Menu.showError("Municipality id must be greater or equal than 0!");
        } while (true);
    }

    public String details(String municipalityName) {
        String s = "";
        s += "Id              : " + this.id + "\n";
        s += "Name            : " + this.name + "\n";
        s += "Owner Name      : " + this.ownerName + "\n";
        s += "Address         : " + this.address + "\n";
        s += "Phone           : " + this.phone + "\n";
        s += "Municipality Id : " + this.municipalityId + " (" + municipalityName + ")" + "\n";
        return s;
    }

    public String tabular(int[] widths) {
        String s = "";
        s += "|" + Menu.align("" + this.id, widths[0], Menu.RIGHT);
        s += "|" + Menu.align(this.name, widths[1], Menu.LEFT);
        s += "|" + Menu.align(this.ownerName, widths[2], Menu.LEFT);
        s += "|" + Menu.align(this.address, widths[3], Menu.LEFT);
        s += "|" + Menu.align(this.phone, widths[4], Menu.LEFT);
        s += "|" + Menu.align("" + this.municipalityId, widths[5], Menu.RIGHT);
        return s + "|\n";
    }

    public String[] options(String municipalityName) {
        String[] options = {
            "Name         : " + this.name,
            "Owner Name   : " + this.ownerName,
            "Address      : " + this.address,
            "Phone        : " + this.phone,
            "Municipality : " + this.municipalityId + " (" + municipalityName + ")",
        };
        return options;
    }
}

package model;

import tools.Menu;
import tools.MyTool;
import tools.SecretKey;

public class User {
    public int    id;
    public String username;
    public String password;
    public String fullname;
    public String email;
    public String phone;
    public int    userType;
    public String salt;

    public User() {
    }

    public User(String[] data) {
        this.id       = Integer.parseInt(data[0]);
        this.username = data[1];
        this.password = data[2];
        this.fullname = data[3];
        this.email    = data[4];
        this.phone    = data[5];
        this.userType = Integer.parseInt(data[6]);
        this.salt     = data[7];
    }

    public void readUsername() {
        this.username = MyTool.readName();
        if (this.username.length() <= 0) this.username = null;
    }

    public void readPassword() {
        this.password = MyTool.readPassword();
    }

    public void readFullname() {
        do {
            this.fullname = MyTool.readName();
        } while (this.fullname.length() < 3);
    }

    public void readEmail() {
        this.email = MyTool.readEmail();
    }

    public void readPhone() {
        this.phone = MyTool.readString("phone");
    }

    public void readUserType() {
        do {
            Menu.showMenu("User Types", userTypes(), 2);
            this.userType = Menu.getOption(userTypes());
            if (this.userType >= 1 && this.userType <= 3) break;
        } while (true);
    }

    public static final String[] userTypes() {
        String[] s = {"Administrator", "Company", "Milkman"};
        return s;
    }

    public void encrypt() {
        this.salt = SecretKey.getSaltvalue(30);
        this.password = SecretKey.generateSecurePassword(this.password, this.salt);
    }

    public boolean checkPassword(String passwordToCheck) {
        return SecretKey.verifyUserPassword(
            passwordToCheck, this.password, this.salt
        );
    }

    public String details() {
        String s = "";
        s += "Id        : " + this.id + "\n";
        s += "Username  : " + this.username + "\n";
        s += "Full name : " + this.fullname + "\n";
        s += "Email     : " + this.email + "\n";
        s += "Phone     : " + this.phone + "\n";
        s += "User Type : " + userTypes()[this.userType - 1] + "\n";
        return s;
    }

    public String tabular(int[] widths) {
        String s = "";
        s += "|" + Menu.align("" + this.id, widths[0], Menu.RIGHT);
        s += "|" + Menu.align(this.username, widths[1], Menu.LEFT);
        s += "|" + Menu.align("***", widths[2], Menu.LEFT);
        s += "|" + Menu.align(this.fullname, widths[3], Menu.LEFT);
        s += "|" + Menu.align(this.email, widths[4], Menu.LEFT);
        s += "|" + Menu.align(this.phone, widths[5], Menu.LEFT);
        s += "|" + Menu.align("" + userTypes()[this.userType - 1], widths[6], Menu.LEFT);
        s += "|" + Menu.align("***", widths[7], Menu.LEFT);
        return s + "|\n";
    }

    public String[] options() {
        String[] options = {
            "Username  : " + this.username,
            "Fullname  : " + this.fullname,
            "Email     : " + this.email,
            "Phone     : " + this.phone,
            "User Type : " + userTypes()[this.userType - 1],
        };
        return options;
    }
}

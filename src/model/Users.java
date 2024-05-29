package model;

import tools.DataTableView;
import tools.Database;
import tools.Menu;
import tools.MyTool;

public class Users {
    Database       _db;
    public User[]  _users;
    public int[]   _widths;
    final String[] _headers = {
        "Id", "Username", "Password", "Full name", 
        "Email", "Phone", "User Type" , "Salt"
    };
    public String  _line;

    public Users(Database db, String filter) {
        this._db = db;
        select(filter);
    }

    private void select(String filter) {
        String[][] data = this._db.select("user", filter);
        this._users = new User[data.length - 1];
        for (int i = 1; i < data.length; i++) {
            this._users[i - 1] = new User(data[i]);
        }
        this._widths = MyTool.initWiths(this._headers);
    }

    public User get(int id) {
        for (User user : this._users) {
            if (user.id == id) return user;
        }
        return null;
    }

    public User get(String name) {
        for (User user : this._users) {
            if (user.username.equalsIgnoreCase(name)) 
                return user;
        }
        return null;
    }

    public boolean exists(String name) {
        for (User user : this._users) {
            if (user.username.equals(name)) return true;
        }
        return false;
    }

    public String[] tabular() {
        String[] data = new String[this._users.length];
        for (int i = 0; i < this._users.length; i++) {
            data[i] = this._users[i].tabular(this._widths);
        }
        return data;
    }

    public void updateWidths() {
        this._widths = MyTool.initWiths(this._headers);
        for (User user : this._users) {
            if (this._widths[0] < ("" + user.id).length()) {
                this._widths[0] = ("" + user.id).length();
            }
            if (this._widths[1] < user.username.length()) {
                this._widths[1] = user.username.length();
            }
            /*if (this._widths[2] < user.password.length()) {
                this._widths[2] = user.password.length();
            }*/
            if (this._widths[3] < user.fullname.length()) {
                this._widths[3] = user.fullname.length();
            }
            if (this._widths[4] < user.email.length()) {
                this._widths[4] = user.email.length();
            }
            if (this._widths[5] < user.phone.length()) {
                this._widths[5] = user.phone.length();
            }
            if (this._widths[6] < User.userTypes()[user.userType - 1].length()) {
                this._widths[6] = User.userTypes()[user.userType - 1].length();
            }
            /*if (this._widths[7] < user.salt.length()) {
                this._widths[7] = user.salt.length();
            }*/
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

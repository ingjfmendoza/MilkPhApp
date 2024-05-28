/*
 * 
 * @author JF Mendoza M
 * @email jfmendozam@gmail.com
 * @email juan.mendoza@usantoto.edu.co
 * @license GNU General Public License
 * @version 1.0
*/

package tools;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class Database {
    Connection _conn;

    public Database(String url) {
        this._conn = null;
        connect(url);
    }

    /**
     * This method establishes a connection to the SQLite database using the provided URL.
     *
     * @param url The URL of the SQLite database.
     *
     * @throws Exception If an error occurs while connecting to the database.
     *
     * @see DriverManager#getConnection(String)
     * @see Class#forName(String)
     */
    public void connect(String url) {
        try {
            // Load the SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");
            
            // Establish a connection to the SQLite database
            _conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            // Print the error message if an exception occurs
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method retrieves the names of all fields in the provided model object.
     *
     * @param model The model object from which to retrieve field names.
     *
     * @return An array of strings containing the names of all fields in the model object.
     */
    public static String[] getModelInfo(Object model) {
        Field[] metaFields = model.getClass().getDeclaredFields();
        String[] fields = new String[metaFields.length];
        int counter = 0;
        for (Field field : metaFields) {
            try {
                field.setAccessible(true);
                fields[counter++] = field.getName();
            } catch (Exception e) {}
        }
        return fields;
    }

    /**
     * This method inserts a new record into the specified SQLite table.
     *
     * @param model The object containing the fields to be inserted into the table.
     *
     * @return True if the insertion was successful, false otherwise.
     */
    @SuppressWarnings("deprecation")
    public boolean insert(Object model) {
        String sql1 = "INSERT INTO " + model.getClass().getSimpleName() + "(";
        String sql2 = "VALUES (";

        Field[] fields = model.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if (!field.getName().equals("id")) {
                    sql1 += field.getName() + ", ";
                    if (field.getType().equals(String.class)) {
                        sql2 += "\"" + field.get(model).toString() + "\", ";
                    } else if (field.getType().equals(int.class)) {
                        sql2 += Integer.parseInt(field.get(model).toString()) + ", ";
                    } else if (field.getType().equals(Date.class)) {
                        sql2 += DateFormat.getDateInstance(
                                DateFormat.MEDIUM,
                                new Locale("es", "es-CO")
                        ).format(field.get(model)) + ", ";
                    } else if (field.getType().equals(float.class)) {
                        sql2 += Float.parseFloat(field.get(model).toString()) + ", ";
                    } else if (field.getType().equals(double.class)) {
                        sql2 += Double.parseDouble(field.get(model).toString()) + ", ";
                    }
                }
            } catch (IllegalArgumentException e) {
                return false;
            } catch (IllegalAccessException e) {
                return false;
            }
        }
        String sql = sql1.substring(0, sql1.length() - 2) + ")\n";
        sql += sql2.substring(0, sql2.length() - 2) + ");";

        try (Statement statement = this._conn.createStatement()) {
            statement.executeUpdate(sql);
            statement.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * This method retrieves the number of columns in the result set of the provided SQL statement.
     *
     * @param sql The SQL statement for which to retrieve the number of columns.
     *
     * @return The number of columns in the result set of the provided SQL statement. If an error occurs, it returns -1.
     */
    public int getColsCount(String sql) {
        try (Statement statement = this._conn.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int cols = rsmd.getColumnCount();
            return cols;
        }
        catch (SQLException e) {
            return -1;
        }
    }

    /**
     * This method retrieves the number of rows in the result set of the provided SQL statement.
     *
     * @param model The name of the table to query.
     * @param filter An optional filter clause to be appended to the SQL statement.
     *
     * @return The number of rows in the result set of the provided SQL statement. If an error occurs, it returns -1.
     */
    public int getRowsCount(String model, String filter) {     
        String sql = "SELECT count(*) FROM " + model + filter + ";"; 
        try (Statement statement = this._conn.createStatement()) {
            ResultSet rs = statement.executeQuery(sql);
            int rows = -1;
            if (rs.next()) {
                rows = rs.getInt(1);
            }
            statement.close();
            return rows;
        } catch (SQLException e) {
            return -1;
        }
    }

    /**
     * This method retrieves all records from the specified SQLite table (according filter), along with their column names.
     *
     * @param model The name of the table to query.
     * @param filter An optional filter clause to be appended to the SQL statement.
     *
     * @return A 2D array of strings, where the first row contains the column names and the subsequent rows contain the data.
     *         If an error occurs, it returns null.
     */
    public String[][] select(String model, String filter) {
        if (filter.length() > 0) filter = " WHERE " + filter;
        String sql = "SELECT * FROM " + model + filter + ";";
        int rows = this.getRowsCount(model, filter);
        int cols = this.getColsCount(sql);

        if (rows >= 0) {
            String[][] data = new String[rows + 1][cols];
            try {
                Statement st = this._conn.createStatement();
                ResultSet rs = st.executeQuery(sql);
                ResultSetMetaData rsmd = rs.getMetaData();

                for (int i = 1; i <= cols; i++) {
                    data[0][i - 1] = rsmd.getColumnName(i);
                }

                int row = 1;
                while (rs.next()) {
                    for (int i = 1; i <= cols; i++) {
                        String value = rs.getString(i);
                        data[row][i - 1] = value;
                    }
                    row++;
                }
                return data;
            } catch (SQLException e) {
                return null;
            }
        }
        return null;
    }

    /**
     * This method updates a record in the specified SQLite table.
     *
     * @param id The ID of the record to be updated.
     * @param model The object containing the fields to be updated in the table.
     *
     * @return True if the update was successful, false otherwise.
     */
    @SuppressWarnings("deprecation")
    public boolean update(int id, Object model) {
        String sql = "UPDATE " + model.getClass().getSimpleName() + "\nSET ";

        Field[] fields = model.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                if (!field.getName().equals("id")) {
                    sql += field.getName() + " = ";
                    if (field.getType().equals(String.class)) {
                        sql += "\"" + field.get(model).toString() + "\",\n";
                    } else if (field.getType().equals(int.class)) {
                        sql += Integer.parseInt(field.get(model).toString()) + ",\n";
                    } else if (field.getType().equals(Date.class)) {
                        sql += DateFormat.getDateInstance(
                                DateFormat.MEDIUM,
                                new Locale("es", "es-CO")
                        ).format(field.get(model)) + ",\n";
                    } else if (field.getType().equals(float.class)) {
                        sql += Float.parseFloat(field.get(model).toString()) + ",\n";
                    } else if (field.getType().equals(double.class)) {
                        sql += Double.parseDouble(field.get(model).toString()) + ",\n";
                    }
                }
            } catch (IllegalArgumentException e) {
                return false;
            } catch (IllegalAccessException e) {
                return false;
            }
        }
        sql = sql.substring(0, sql.length() - 2);
        sql += "\nWHERE id = " + id + ";";

        try (Statement st = this._conn.createStatement()) {
            st.executeUpdate(sql);
            st.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * This method deletes a record from the specified SQLite table based on the provided ID.
     *
     * @param id The ID of the record to be deleted.
     * @param model The name of the table from which to delete the record.
     *
     * @return True if the deletion was successful, false otherwise.
     */
    public boolean delete(int id, String model) {
        String sql = "DELETE FROM " + model + " " +
            "WHERE id = " + id + ";";
        try (Statement st = this._conn.createStatement()) {
            st.execute(sql);
            st.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}

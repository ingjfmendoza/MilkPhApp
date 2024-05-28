package tools;

public class DataTableView {
    public DataTableView() { }


    public static void showTable(String[][] table) {
        int cols = table[0].length;
        int[] widths = new int[cols];
        
        for (int i = 0; i < cols; i++) {
            String[] column = getCol(i, table);
            widths[i] = Menu.getMaxLen(column[0], column);
        }
        
        String line = getHorizontalLine(widths);
        System.out.println(line);

        for (int i = 0; i < cols; i++) {
            System.out.print(
                "|" + 
                Menu.align(table[0][i], widths[i], Menu.CENTER)
            );
        }
        System.out.println("|");
        System.out.println(line);

        for (int i = 1; i < table.length; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(
                    "|" + 
                    Menu.align(table[i][j], widths[j], Menu.LEFT)
                );
            }
            System.out.println("|");
        }
        System.out.println(line);
    }

    public static String getHorizontalLine(int[] widths) {
        String line = "";
        for (int w: widths) {
            line += "+" + "-".repeat(w);
        }
        return line + "+";
    }

    private static String[] getCol(int col, String[][] table) {
        String[] column;
        int rows = table.length;
        column = new String[rows];

        for (int i = 0; i < rows; i++) {
            column[i] = table[i][col];
        }
        return column;
    }
}

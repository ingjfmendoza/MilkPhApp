import controller.MainController;

public class App {

    public static void main(String[] args) throws Exception {
        String dbConnect = "jdbc:sqlite:";
        String dbName = "db/milkph.db";

        MainController mc = new MainController(dbConnect + dbName);
        mc.login();
    }
}

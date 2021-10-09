package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static DBManager instance;
    private Connection con;

    private DBManager() throws SQLException {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/db?user=user&password=password");
            createStatements();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private static DBManager getInstance() throws SQLException {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public static Connection getCon() throws SQLException {
        return getInstance().con;
    }

    private void createStatements() {

    }

    public static void disconnect() {
        if (instance != null) {
            try {
                instance.con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}

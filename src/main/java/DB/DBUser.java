package DB;

import BO.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class DBUser extends BO.User{
    private static final String searchByName = "SELECT * FROM t_user WHERE email = ?";
    private static final String searchById = "SELECT * FROM t_user WHERE id = ?";
    private static final String searchByType = "SELECT * FROM t_user WHERE type = ?";

    private DBUser(int id ,String type, String username, String password) {
        super(id, type, username, password);
    }

    public static User getUserByEmail(String search) throws SQLException {
        return getUser(search,searchByName);
    }

    public static User getUserByID(String search) throws SQLException {
        return getUser(search, searchById);
    }

    private static User getUser(String search, String searchBy) throws SQLException {
        User result = null;
        try {
            Connection con = DBManager.getCon();
            PreparedStatement st  = con.prepareStatement(searchBy);
            st.setString(1, search);
            ResultSet rs =  st.executeQuery();
            if (rs.next()) {
                result = new DBUser(rs.getInt("id"),
                                rs.getString("type"),
                                rs.getString("email"),
                                rs.getString("password"));
            }
        } catch (SQLException e) {
            throw new SQLException("");
        }
        return result;
    }

    private static Collection<User> getUsers(String search, String searchBy) {
        ArrayList<User> collection = new ArrayList<>();
        try {
            Connection con = DBManager.getCon();
            PreparedStatement st = con.prepareStatement(searchBy);
            st.setString(1,search);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String type = rs.getString("type");
                String username = rs.getString("username");
                String password = rs.getString("password");

                collection.add(new DBUser(id, type,username,password));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return collection;
    }
}

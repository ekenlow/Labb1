package DB;

import BO.Type;
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
    private static final String insertNewUser = "INSERT INTO db.t_user (email, password, type) VALUES (?,?,?)";

    private DBUser(int id ,String type, String username, String password) {
        super(id, type, username, password);
    }

    public static DBUser createUser(String username, String password, Type type) throws SQLException {
        try {
            Connection con = DBManager.getCon();
            PreparedStatement st = con.prepareStatement(insertNewUser);
            st.setString(1,username);
            st.setString(2,password);
            st.setString(3, String.valueOf(type));
            st.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return getUser(username,searchByName);
    }

    public static DBUser getUserByEmail(String search) throws SQLException {
        return getUser(search,searchByName);
    }

    public static DBUser getUserByID(String search) throws SQLException {
        return getUser(search, searchById);
    }

    private static DBUser getUser(String search, String searchBy) throws SQLException {
        DBUser result = null;
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

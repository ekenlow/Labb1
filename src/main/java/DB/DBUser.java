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
    private static final String setType = "UPDATE t_user SET type = ? WHERE id = ?";
    private static final String insertNewUser = "INSERT INTO db.t_user (email, password, type) VALUES (?,?,?)";
    private static final String searchAll = "SELECT * FROM t_user";

    private DBUser(int id ,String type, String username, String password) {
        super(id, type, username, password);
    }

    public static DBUser createUser(String username, String password, Type type) throws SQLException {
            Connection con = DBManager.getCon();
            PreparedStatement st = con.prepareStatement(insertNewUser);
            st.setString(1,username);
            st.setString(2,password);
            st.setString(3, String.valueOf(type));
            st.execute();
        return getUser(username,searchByName);
    }

    public static DBUser getUserByEmail(String search) throws SQLException {
        return getUser(search,searchByName);
    }

    public static DBUser getUserById(int id) throws SQLException {
        return getUser(String.valueOf(id), searchById);
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

    public static Collection<User>getAll() throws SQLException {
        Connection con = DBManager.getCon();
        PreparedStatement st = con.prepareStatement(searchAll);
        ResultSet rs = st.executeQuery();

        return getUserFromResults(rs);
    }

    private static Collection<User> getUserFromResults(ResultSet rs) throws SQLException {
        ArrayList<User> collection = new ArrayList<>();
        while (rs.next()){
            int id = rs.getInt("id");
            String type = rs.getString("type");
            String username = rs.getString("email");
            String password = rs.getString("password");

            collection.add(new DBUser(id, type,username,password));
        }
        return collection;
    }

    private static Collection<User> getUsers(String search, String searchBy) {
        ArrayList<User> collection = new ArrayList<>();
        try {
            Connection con = DBManager.getCon();
            PreparedStatement st = con.prepareStatement(searchBy);
            st.setString(1,search);
            ResultSet rs = st.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return collection;
    }


    public static void setType(int id, Type newType) throws SQLException {
        Connection con = DBManager.getCon();
        PreparedStatement st = con.prepareStatement(setType);
        st.setString(1, newType.toString());
        st.setInt(2, id);
        st.execute();
    }
}

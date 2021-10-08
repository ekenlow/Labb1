package BO;

import View.ItemInfo;
import View.UserInfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class UserHandler {
    public static UserInfo getByName(String search) throws SQLException {
        User res = User.getUserByEmail(search);
        return new UserInfo(res.getId(),res.getType(), res.getUsername());
    }

    public static boolean login(String email, String password) throws SQLException, NullPointerException {
        return User.getUserByEmail(email).checkPassword(password);
    }

    public static UserInfo createUser(String username, String password) throws SQLException {
        User user =  User.createUser(username,password,Type.USER);
        return new UserInfo(user.getId(), user.getType(),user.getUsername());
    }

    public static ArrayList<UserInfo> getAll() throws SQLException {
        ArrayList<User> users = (ArrayList<User>) User.getAll();
        return users.stream().map(user -> new UserInfo(user.getId(), user.getType(), user.getUsername())).collect(Collectors.toCollection(ArrayList::new));
    }

    public static boolean setType(int id, Type newType) {
        try {
            if(User.getUserById(id).getType().equals(newType)){
                return false;
            }
            User.setType(id, newType);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}

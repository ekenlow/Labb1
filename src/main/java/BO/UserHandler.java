package BO;

import View.UserInfo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class UserHandler {
    public static UserInfo getByName(String search) throws SQLException {
        User res = User.getUserByEmail(search);
        return new UserInfo(res.getType(), res.getUsername());
    }

    public static boolean login(String email, String password) throws SQLException, NullPointerException {
        return User.getUserByEmail(email).checkPassword(password);
    }
}

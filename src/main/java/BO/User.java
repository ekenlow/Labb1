package BO;

import DB.DBUser;

import java.sql.SQLException;
import java.util.Collection;

public class User {
    private int id;
    private Type type;
    private String username;
    private String password;

    protected User(int id, String type, String username, String password) {
        this.id = id;
        this.type = Type.valueOf(type);
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean checkPassword(String password) {
        return password.equals(this.password);
    }
    public static User getUserByEmail(String search) throws SQLException {
        return DBUser.getUserByEmail(search);
    }


}

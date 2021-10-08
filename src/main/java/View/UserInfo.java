package View;

import BO.Type;
import DB.DBUser;

public class UserInfo {

    private final int id;
    private final Type type;
    private final String username;

    public UserInfo(int id, Type type, String username) {
        this.id=id;
        this.type = type;
        this.username = username;
    }

    public Type getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", type=" + type +
                ", username='" + username + '\'' +
                '}';
    }
}

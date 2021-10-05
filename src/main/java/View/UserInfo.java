package View;

import BO.Type;
import DB.DBUser;

public class UserInfo {

    private final Type type;
    private final String username;

    public UserInfo(Type type, String username) {
        this.type = type;
        this.username = username;
    }

    public Type getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }


    @Override
    public String toString() {
        return "UserInfo{" +
                "type=" + type +
                ", username='" + username + '\'' +
                '}';
    }
}

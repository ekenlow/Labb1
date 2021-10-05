package DB;

import BO.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;


public class DBItem extends BO.Item {
    private static final String searchByName = "SELECT * FROM t_item WHERE name = ?";
    private static final String searchById = "SELECT * FROM t_item WHERE id = ?";
    private static final String searchByType = "SELECT * FROM t_item WHERE type = ?";

    public static Collection getByName(String search) {
        return getItems(search, searchByName);
    }
    public static DBItem getById(String search) {
        return getItem(search, searchById);
    }
    public static Collection getByType(String search){
        return getItems(search,searchByType);
    }


    private static DBItem getItem(String search, String searchBy) {
        DBItem item = null;
        try {
            Connection con = DBManager.getCon();
            PreparedStatement st = con.prepareStatement(searchBy);
            st.setString(1,search);
            ResultSet rs = st.executeQuery();
            if (rs.next()){
                item = new DBItem(rs.getInt("id"),
                                rs.getString("type"),
                                rs.getString("name"),
                                rs.getInt("stock"),
                                rs.getFloat("price"));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return item;
    }

    private static Collection<Item> getItems(String search, String searchBy) {
        ArrayList<Item> collection = new ArrayList<>();
        try {
            Connection con = DBManager.getCon();
            PreparedStatement st = con.prepareStatement(searchBy);
            st.setString(1,search);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                int id = rs.getInt("id");
                String type = rs.getString("type");
                String name = rs.getString("name");
                int stock = rs.getInt("stock");
                float price = rs.getFloat("price");

                collection.add(new DBItem(id,type,name,stock,price));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return collection;
    }

    protected DBItem(int id, String type, String name, int stock, float price){
        super(id,type,name,stock,price);
    }
}

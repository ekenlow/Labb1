package DB;

import BO.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;


public class DBItem extends BO.Item {
    private static final String searchByName = "SELECT * FROM t_item WHERE name = ?";

    public static Collection getByName(String search) {
        ArrayList<BO.Item> collection = new ArrayList<>();
        try {
            Connection con = DBManager.getCon();
            PreparedStatement st = con.prepareStatement(searchByName);
            st.setString(1, search);
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

package DB;

import BO.Item;
import BO.Order;
import BO.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBOrder extends BO.Order{
    private static final String searchByName = "SELECT * FROM t_order WHERE id = ?";



    protected DBOrder(int id, Status status, ArrayList<Item> items) {
        super(id, status, items);
    }
    public static ArrayList<Order> getById(int id){
        return null;
        /*
        ArrayList<BO.Item> collection = new ArrayList<>();
        try {
            Connection con = DBManager.getCon();
            PreparedStatement st = con.prepareStatement(id);
            st.setString(1, search);
            ResultSet rs = st.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                String type = rs.getString("type");
                String name = rs.getString("name");
                int stock = rs.getInt("stock");
                float price = rs.getFloat("price");

                collection.add(new DBOrder(id,type,name,stock,price));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return collection;

         */
    }

}

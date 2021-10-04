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
    private static final String searchById = "SELECT * FROM t_order WHERE id = ?";



    protected DBOrder(int id, Status status, ArrayList<Item> items) {
        super(id, status, items);
    }
    public static ArrayList<Order> getById(int search){
        //return null;
        ArrayList<BO.Order> collection = new ArrayList<>();
        try {
            Connection con = DBManager.getCon();
            PreparedStatement st = con.prepareStatement(searchById);
            st.setString(1, String.valueOf(search));
            ResultSet rs = st.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                Status status = Status.valueOf(rs.getString("status"));
                ArrayList<Item> items = (ArrayList<Item>) DBItem.getById(String.valueOf(search));

                collection.add(new DBOrder(id,status,items));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return collection;
    }

}

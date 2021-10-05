package DB;

import BO.Item;
import BO.Order;
import BO.Status;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class DBOrder extends BO.Order{
    private static final String searchById = "SELECT * FROM t_order WHERE id = ?";


    protected DBOrder(int id, String status, ArrayList<Item> items) {
        super(id, status, items);
    }
    public static Order getById(String search) throws SQLException {
        return getOrder(search,searchById);

    }

    private static DBOrder getOrder(String search, String searchBy) throws SQLException {
        Connection con = DBManager.getCon();
        PreparedStatement st = con.prepareStatement(searchBy);
        st.setString(1,search);
        ResultSet rs = st.executeQuery();

        rs.next();
        int id = rs.getInt("id");
        String status = rs.getString("status");
        ArrayList<Item> items = new ArrayList<>();

        PreparedStatement it = con.prepareStatement("SELECT * FROM t_order_item WHERE order_id = ?");
        rs = it.executeQuery();
        while (rs.next()) {
            items.add(Item.getById(String.valueOf(rs.getInt("item_id"))));
        }

        return new DBOrder(id,status,items);


    }

}

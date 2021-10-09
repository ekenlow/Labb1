package DB;

import BO.*;
import View.ItemInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class DBOrder extends BO.Order{
    private static final String searchById = "SELECT * FROM t_order WHERE id = ?";
    private static final String getItems = "SELECT * FROM t_order_item WHERE order_id = ?";
    private static final String insertOrder = "INSERT INTO db.t_order () VALUES ()";
    private static final String insertOrderItem = "INSERT INTO db.t_order_item (order_id, item_id, count) VALUES (?, ?, ?)";
    private static final String getOrderId = "SELECT id FROM t_order";


    protected DBOrder(int id, String status, ArrayList<Item> items, HashMap<Integer, Integer> counts) {
        super(id, status, items, counts);
    }
    public static Order getById(String search) throws SQLException {
        return getOrder(search);

    }

    private static Order getOrder(String orderIdStr) throws SQLException {
        Connection con = DBManager.getCon();

        PreparedStatement orderSt = con.prepareStatement(searchById);
        orderSt.setInt(1, Integer.parseInt(orderIdStr));
        ResultSet orderRs = orderSt.executeQuery();

        PreparedStatement orderItemSt = con.prepareStatement(getItems);
        orderItemSt.setInt(1,Integer.parseInt(orderIdStr));
        ResultSet orderItemRs = orderItemSt.executeQuery();

        orderRs.next();
        String status = orderRs.getString("status");

        HashMap<Integer, Integer> itemCounts = new HashMap<>();
        ArrayList<Item> items = new ArrayList<>();
        while (orderItemRs.next()) {
            int itemId = orderItemRs.getInt("item_id");
            int count = orderItemRs.getInt("count");
            itemCounts.put(itemId, count);
            items.add(DBItem.getById(String.valueOf(itemId)));
        }
        return new DBOrder(Integer.parseInt(orderIdStr), status, items, itemCounts);

    }

    public static boolean createOrder(HashMap<Integer, Integer> cart) throws SQLException {
        Connection con = DBManager.getCon();
        con.setAutoCommit(false);
        try {
            PreparedStatement orderSt = con.prepareStatement(insertOrder, PreparedStatement.RETURN_GENERATED_KEYS);

            orderSt.executeUpdate();
            ResultSet rsKeys = orderSt.getGeneratedKeys();
            rsKeys.next();
            int orderId = rsKeys.getInt(1);

            PreparedStatement orderItemSt = con.prepareStatement(insertOrderItem);
            for (Integer itemId : cart.keySet()) {
                if( (DBItem.getStockById(itemId) - cart.get(itemId)) <0)
                    throw new SQLException();
                orderItemSt.setInt(1, orderId);
                orderItemSt.setInt(2, itemId);
                orderItemSt.setInt(3, cart.get(itemId));
                orderItemSt.executeUpdate();
                DBItem.setStock((cart.get(itemId) * -1), itemId);
            }
            con.commit();
            return true;
        } catch (SQLException e) {
            con.rollback();
            throw new SQLException();
        }finally {
            con.setAutoCommit(true);
        }
    }

    public static Collection<Order> getAll() throws SQLException {
        Connection con = DBManager.getCon();
        PreparedStatement st = con.prepareStatement(getOrderId);
        ResultSet rs = st.executeQuery();
        ArrayList<Order> orders = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            orders.add(getOrder(String.valueOf(id)));
        }
        return orders;
    }

    public static boolean setStatus(int id, Status newStatus) throws SQLException {
        Connection con = DBManager.getCon();
        PreparedStatement st =con.prepareStatement("UPDATE t_order SET status = ? WHERE id = ?");
        st.setString(1, newStatus.name());
        st.setInt(2, id);
        st.executeUpdate();
        return true;
    }
}

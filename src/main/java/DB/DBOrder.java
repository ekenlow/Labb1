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
import java.util.List;

public class DBOrder extends BO.Order{
    private static final String searchById = "SELECT * FROM t_order WHERE id = ?";
    private static final String getItems = "SELECT * FROM t_order_item WHERE order_id = ?";
    private static final String insertOrder = "INSERT INTO db.t_order () VALUES ()";
    private static final String insertOrderItem = "INSERT INTO db.t_order_item (order_id, item_id, count) VALUES (?, ?, ?)";
    private static final String getOrderId = "SELECT id FROM t_order";
    private static final String update = "UPDATE t_order SET status = ? WHERE id = ?";


    protected DBOrder(int id, String status, ArrayList<Item> items, HashMap<Integer, Integer> counts) {
        super(id, status, items, counts);
    }

    public static Order getByIdDB(String search) throws SQLException {
        return getOrder(search);
    }

    private static DBOrder getOrder(String orderIdStr) throws SQLException {
        Connection con = DBManager.getCon();
        try (PreparedStatement orderSt = con.prepareStatement(searchById);
             PreparedStatement orderItemSt = con.prepareStatement(getItems)) {

            orderSt.setInt(1, Integer.parseInt(orderIdStr));
            ResultSet orderRs = orderSt.executeQuery();


            orderItemSt.setInt(1, Integer.parseInt(orderIdStr));
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
    }

    public static boolean createOrderDB(HashMap<Integer, Integer> cart) throws SQLException {
        Connection con = DBManager.getCon();
        con.setAutoCommit(false);
        try (PreparedStatement orderSt = con.prepareStatement(insertOrder, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement orderItemSt = con.prepareStatement(insertOrderItem)) {

            orderSt.executeUpdate();
            ResultSet rsKeys = orderSt.getGeneratedKeys();
            rsKeys.next();
            int orderId = rsKeys.getInt(1);


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

    public static List<DBOrder> getAllDB() throws SQLException {
        Connection con = DBManager.getCon();
        try (PreparedStatement st = con.prepareStatement(getOrderId)) {
            ResultSet rs = st.executeQuery();
            ArrayList<DBOrder> orders = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("id");
                orders.add(getOrder(String.valueOf(id)));
            }
            return orders;
        }
    }

    public static boolean setStatusDB(int id, Status newStatus) throws SQLException {
        Connection con = DBManager.getCon();
        try (PreparedStatement st =con.prepareStatement(update)) {
            st.setString(1, newStatus.name());
            st.setInt(2, id);
            st.executeUpdate();
            return true;
        }
    }
}

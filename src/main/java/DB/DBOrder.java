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
    private static final String searchForWares = "SELECT * FROM t_orders_wares WHERE order_id = ?";



    protected DBOrder(int id, Status status, ArrayList<Item> items) {
        super(id, status, items);
    }

    public static ArrayList<Order> getById(int id){
        ArrayList<Order> collection = new ArrayList<>();
        /*try { // Behövs inte än. Börja med att kunna logga in och kolla över varor.
            Connection con = DBManager.getCon();
            PreparedStatement stOrder = con.prepareStatement(searchById);
            PreparedStatement stWares = con.prepareStatement(searchForWares);
            stOrder.setInt(1, id);
            stWares.setInt(1, id);
            ResultSet rsOrders = stOrder.executeQuery();
            ResultSet rsWares = stWares.executeQuery();

            while (rsOrders.next()){
                int orderId = rsOrders.getInt("id");
                Status status = rsOrders.getObject("status", Status.class);
                ArrayList<Item> items = new ArrayList<>();
                while (rsWares.next()) {
                    int wareId = rsWares.getInt("id");
                    String type = rsWares.getString("type");
                    String name = rsWares.getString("name");
                    int stock = rsWares.getInt("stock");
                    float price = rsWares.getFloat("price");
                    //items.add(new Item(rs))
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }*/
        return collection;

    }

}

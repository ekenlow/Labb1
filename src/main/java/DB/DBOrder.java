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
        return null;

    }

}

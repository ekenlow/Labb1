package DB;

import BO.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;


public class DBItem extends BO.Item {
    private static final String searchByName = "SELECT * FROM t_item WHERE name = ?";
    private static final String searchById = "SELECT * FROM t_item WHERE id = ?";
    private static final String searchByType = "SELECT * FROM t_item WHERE type = ?";
    private static final String searchAll = "SELECT * FROM t_item";

    public static Collection getByName(String search) {
        return getItems(search, searchByName);
    }

    public static DBItem getById(String search) {
        return getItem(search, searchById);
    }

    public static Collection getByType(String search) {
        return getItems(search, searchByType);
    }

    public static Collection getAll() throws SQLException {
        return getItems();
    }


    private static DBItem getItem(String search, String searchBy) {
        DBItem item = null;
        try {
            Connection con = DBManager.getCon();
            PreparedStatement st = con.prepareStatement(searchBy);
            st.setString(1, search);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                item = new DBItem(rs.getInt("id"),
                        rs.getString("type"),
                        rs.getString("name"),
                        rs.getInt("stock"),
                        rs.getFloat("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return item;
    }

    private static Collection<Item> getItems(String search, String searchBy) {
        ArrayList<Item> collection = new ArrayList<>();
        try {
            Connection con = DBManager.getCon();
            PreparedStatement st = con.prepareStatement(searchBy);
            st.setString(1, search);
            ResultSet rs = st.executeQuery();
            collection = (ArrayList<Item>) getItemFromResults(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return collection;
    }


    private static Collection<Item> getItems() throws SQLException {
        Connection con = DBManager.getCon();
        PreparedStatement st = con.prepareStatement(searchAll);
        ResultSet rs = st.executeQuery();
        return getItemFromResults(rs);
    }

    private static Collection<Item> getItemFromResults(ResultSet rs) throws SQLException {
        ArrayList<Item> collection = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String type = rs.getString("type");
            String name = rs.getString("name");
            int stock = rs.getInt("stock");
            float price = rs.getFloat("price");

            collection.add(new DBItem(id, type, name, stock, price));
        }
        return collection;
    }

    public static void setStock(int lessStock, int id) throws SQLException {
        Connection con = DBManager.getCon();
        PreparedStatement stckSt = con.prepareStatement("SELECT stock FROM t_item WHERE id = ?");
        stckSt.setInt(1, id);
        ResultSet stockRs = stckSt.executeQuery();
        stockRs.next();
        int newStock = stockRs.getInt("stock") + lessStock;

        PreparedStatement newStockSt = con.prepareStatement("UPDATE t_item SET stock = ? WHERE id = ?");
        newStockSt.setInt(1, newStock);
        newStockSt.setInt(2, id);
        newStockSt.executeUpdate();
    }

    protected DBItem(int id, String type, String name, int stock, float price) {
        super(id, type, name, stock, price);
    }

    public static void updateItem(int id, String name, String type, int stock, float price) throws SQLException {
        Connection con = DBManager.getCon();
        PreparedStatement st = con.prepareStatement("UPDATE t_item SET name = ?, type = ?, stock = ?, price = ? WHERE id = ?");
        st.setString(1, name);
        st.setString(2, type);
        st.setInt(3, stock);
        st.setFloat(4, price);
        st.setInt(5, id);
        st.executeUpdate();
        st.close();
    }

    public static void createItem(String name, String type, int stock, float price) throws SQLException {
        Connection con = DBManager.getCon();
        PreparedStatement st = con.prepareStatement("INSERT INTO t_item (name, type, price, stock) VALUES (?, ?, ?, ?)");
        st.setString(1, name);
        st.setString(2, type);
        st.setInt(3, stock);
        st.setFloat(4, price);
        st.executeUpdate();
        st.close();
    }

    public static void deleteItem(int id) throws SQLException{
        Connection con = DBManager.getCon();
        PreparedStatement st = con.prepareStatement("DELETE FROM t_item WHERE id = ? ");
        st.setInt(1, id);
        st.executeUpdate();
    }

    public static int getStockById(int itemId) throws SQLException {
        return getById(String.valueOf(itemId)).getStock();
    }
}

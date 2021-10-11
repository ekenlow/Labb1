package DB;

import BO.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DBItem extends BO.Item {
    private static final String searchByName = "SELECT * FROM t_item WHERE name = ?";
    private static final String searchById = "SELECT * FROM t_item WHERE id = ?";
    private static final String searchByType = "SELECT * FROM t_item WHERE type = ?";
    private static final String searchAll = "SELECT * FROM t_item";
    private static final String delete = "DELETE FROM t_item WHERE id = ? ";
    private static final String create = "INSERT INTO t_item (name, type, price, stock) VALUES (?, ?, ?, ?)";
    private static final String update = "UPDATE t_item SET name = ?, type = ?, stock = ?, price = ? WHERE id = ?";
    private static final String setStock = "SELECT stock FROM t_item WHERE id = ?";

    public static List<DBItem> getByName(String search) {
        return getItems(search, searchByName);
    }

    public static DBItem getById(String search) throws SQLException {
        return getItem(search, searchById);
    }

    public static List<Item> getByType(String search) throws SQLException {
        return getItems(search, searchByType);
    }

    public static List<DBItem> getAll() throws SQLException {
        return getItems();
    }


    private static DBItem getItem(String search, String searchBy) throws SQLException {
        DBItem item = null;
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
        return item;
    }

    private static List<DBItem> getItems(String search, String searchBy) throws SQLException {
        ArrayList<DBItem> collection = new ArrayList<>();
            Connection con = DBManager.getCon();
            PreparedStatement st = con.prepareStatement(searchBy);
            st.setString(1, search);
            ResultSet rs = st.executeQuery();
            collection = (ArrayList<DBItem>) getItemFromResults(rs);

        return collection;
    }


    private static List<DBItem> getItems() throws SQLException {
        Connection con = DBManager.getCon();
        PreparedStatement st = con.prepareStatement(searchAll);
        ResultSet rs = st.executeQuery();
        return getItemFromResults(rs);
    }

    private static List<DBItem> getItemFromResults(ResultSet rs) throws SQLException {
        ArrayList<DBItem> collection = new ArrayList<>();
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
        PreparedStatement stckSt = con.prepareStatement(setStock);
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
        PreparedStatement st = con.prepareStatement(update);
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
        PreparedStatement st = con.prepareStatement(create);
        st.setString(1, name);
        st.setString(2, type);
        st.setFloat(3, price);
        st.setInt(4, stock);
        st.executeUpdate();
        st.close();
    }

    public static void deleteItem(int id) throws SQLException{
        Connection con = DBManager.getCon();
        PreparedStatement st = con.prepareStatement(delete);
        st.setInt(1, id);
        st.executeUpdate();
    }

    public static int getStockById(int itemId) throws SQLException {
        return getById(String.valueOf(itemId)).getStock();
    }
}

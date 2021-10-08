package View;

import BO.*;
import DB.DBManager;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class Controller extends HttpServlet {

    private static final String home = "/Labb1_war_exploded/";

    public void init() {
        DBManager.getCon();
    }


    @Override // https://stackoverflow.com/questions/9500051/multiple-method-calling-using-single-servlet
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        if (req.getParameter("login") != null) {
            login(req, resp, session);
        } else if (req.getParameter("register") != null) {
            registerUser(req, session);
        } else if (req.getParameter("logout") != null) {
            session.setAttribute("user", null);
        } else if (req.getParameter("itemId") != null) {
            addItemToCart(req, session);
        } else if (req.getParameter("clearCart") != null) {
            clearCart(req, session);
        } else if (req.getParameter("checkOut") != null) {
            checkoutItems(req, session);
        } else if (req.getParameter("goToCart") != null) {
            UserInfo user = (UserInfo) session.getAttribute("user");
            if (user == null) {
                session.setAttribute("error", "You need to login");

            } else {
                if (user.getType() == Type.USER) {
                    resp.sendRedirect(home + "cart.jsp");
                    return;
                } else {
                    session.setAttribute("error", "Wrong acecces level");
                }
            }
        } else if (req.getParameter("removeId") != null) {
            removeItemFromCart(req, session);
        } else if (req.getParameter("updateUser") != null) {
            int id = Integer.parseInt(req.getParameter("updateUser"));
            Type type = Type.valueOf(req.getParameter("roles"));
            if (!UserHandler.setType(id, type)) {
                session.setAttribute("errorAdmin", "Failed to update role");
            }
        } else if (req.getParameter("updateOrder") != null) {
            String orderId = (req.getParameter("updateOrder"));
            try {
                OrderInfo order = OrderHandler.getById(orderId);
                switch (order.getStatus().name()) {
                    case "ORDERED":
                        OrderHandler.setStatus(order.getId(), Status.PICKING);
                        break;
                    case "PICKING":
                        OrderHandler.setStatus(order.getId(), Status.SENT);
                        break;
                    default:
                }
            } catch (SQLException e) {
                session.setAttribute("errorOrder", "Error processing orders");
            }
        }else if(req.getParameter("goToItems") !=null){
            UserInfo user = (UserInfo) session.getAttribute("user");
            if (user == null) {
                session.setAttribute("error", "You need to login");

            } else {
                if (user.getType() == Type.ADMIN) {
                    resp.sendRedirect(home + "item.jsp");
                    return;
                } else {
                    session.setAttribute("error", "Wrong acecces level");
                }
            }
        }else if (req.getParameter("updateItem")!=null) {
            int id = Integer.parseInt(req.getParameter("updateItem"));
            String name = req.getParameter("name");
            String type = req.getParameter("type");
            int stock = Integer.parseInt(req.getParameter("stock"));
            float price = Float.parseFloat(req.getParameter("price"));
            try {
                ItemHandler.updateItem(id,name,type,stock,price);
            } catch (SQLException e) {
                session.setAttribute("updateError", "Could not update item");
            }
            resp.sendRedirect(home + "item.jsp");
            return;
        } else if(req.getParameter("addItem") != null){
            String name = req.getParameter("newName");
            String type = req.getParameter("newType");
            int stock = Integer.parseInt(req.getParameter("newStock"));
            float price = Float.parseFloat(req.getParameter("newPrice"));

            try {
                ItemHandler.createItem(name,type,stock,price);
            } catch (SQLException e) {

            }
        }else if (req.getParameter("deleteItem")!= null){
            try {
                ItemHandler.deleteItem(req.getParameter("deleteItem"));
            } catch (SQLException e) {
                e.printStackTrace();
                session.setAttribute("deleteError", "Could not delete item as it is in an existing order");
            }
            resp.sendRedirect(home + "item.jsp");
            return;
        }
        resp.sendRedirect(home);
    }

    private void checkoutItems(HttpServletRequest req, HttpSession session) {
        HashMap<Integer, Integer> cart;
        HashMap<Integer, ItemInfo> cartInfo;
        if (session.getAttribute("cart") != null && session.getAttribute("cartInfo") != null) {
            cart = (HashMap<Integer, Integer>) session.getAttribute("cart");
            cartInfo = (HashMap<Integer, ItemInfo>) session.getAttribute("cartInfo");

            try {
                OrderHandler.checkOut(cart,cartInfo); // TODO: Implement
            } catch (SQLException e) {
                e.printStackTrace();
            }
            clearCart(req, session);
        }else {
            session.setAttribute("errorCart", "Empty cart");
        }
    }

    private void removeItemFromCart(HttpServletRequest req, HttpSession session) {
        HashMap<Integer, Integer> cart;
        HashMap<Integer, ItemInfo> cartInfo;
        if (session.getAttribute("cart") != null || session.getAttribute("cartInfo") != null) {
            cart = (HashMap<Integer, Integer>) session.getAttribute("cart");
            cartInfo = (HashMap<Integer, ItemInfo>) session.getAttribute("cartInfo");
            cart.remove(Integer.parseInt(req.getParameter("removeId")));
            cartInfo.remove(Integer.parseInt(req.getParameter("removeId")));
            if (cart.isEmpty() || cartInfo.isEmpty()) {
                session.removeAttribute("cart");
                session.removeAttribute("cartInfo");
            }
        } else {
            session.setAttribute("error", "No cart");
        }
    }

    private void clearCart(HttpServletRequest req, HttpSession session) {
        HashMap<Integer, Integer> cart;
        HashMap<Integer, ItemInfo> cartInfo;
        if (session.getAttribute("cart") != null && session.getAttribute("cartInfo") != null) {
            cart = (HashMap<Integer, Integer>) session.getAttribute("cart");
            cartInfo = (HashMap<Integer, ItemInfo>) session.getAttribute("cartInfo");
            cart.clear();
            cartInfo.clear();
        } else {
            session.setAttribute("error", "Cart already cleared");
        }
    }

    private void addItemToCart(HttpServletRequest req, HttpSession session) {
        HashMap<Integer, Integer> cart;
        HashMap<Integer, ItemInfo> cartInfo;

        if (session.getAttribute("cart") == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        } else {
            cart = (HashMap<Integer, Integer>) session.getAttribute("cart");
        }
        if (session.getAttribute("cartInfo") == null) {
            cartInfo = new HashMap<>();
            session.setAttribute("cartInfo", cartInfo);
        } else {
            cartInfo = (HashMap<Integer, ItemInfo>) session.getAttribute("cartInfo");
        }


        int id = Integer.parseInt(req.getParameter("itemId"));
        ItemInfo item = ItemHandler.getById(Integer.toString(id));
        if (item.getStock() <= 0) {
            session.setAttribute("error", "Item out of stock");
            return;
        }
        int amount = cart.get(id) == null ? 1 : cart.get(id) + 1;
        if (amount <= item.getStock()) {
            cart.put(id, amount);
            cartInfo.put(id, item);
        } else
            session.setAttribute("error", "Can't add more items");
    }

    private void registerUser(HttpServletRequest req, HttpSession session) {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        try {
            UserInfo u = UserHandler.createUser(username, password);
        } catch (SQLException e) {
            session.setAttribute("error", "That username already exists");
        }
    }

    private boolean authenticate(HttpServletRequest req, HttpServletResponse resp) {
        try {
            return UserHandler.login(req.getParameter("username"), req.getParameter("password"));

        } catch (SQLException | NullPointerException e) {
            System.err.println("No such user");
            return false; // No such user
        }
    }


    public static HashMap<Integer, ItemInfo> getItems() {
        ArrayList<ItemInfo> items = null;
        HashMap<Integer, ItemInfo> map = new HashMap<>();
        try {
            items = (ArrayList<ItemInfo>) ItemHandler.getAll();
            for (ItemInfo item : items) {
                map.put(item.getId(), item);
            }
        } catch (SQLException e) {
            return new HashMap<>();
        }

        return map;
    }

    public static ArrayList<UserInfo> getUsers() {
        ArrayList<UserInfo> collection = new ArrayList<>();
        try {

            collection = UserHandler.getAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return collection;
    }

    public void destroy() {
        DBManager.disconnect();
    }


    private void login(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
        if (authenticate(req, resp)) {
            try {
                UserInfo u = UserHandler.getByName(req.getParameter("username"));
                session.setAttribute("user", u);
            } catch (Exception e) {
                session.setAttribute("error", "Internal server error");
            }
        } else {
            session.setAttribute("error", "Username or password incorrect");
        }
    }

    public static ArrayList<OrderInfo> getOrders() {
        ArrayList<OrderInfo> orders = null;
        HashMap<Integer, ItemInfo> map = new HashMap<>();
        try {
            orders = (ArrayList<OrderInfo>) OrderHandler.getAll();
        } catch (SQLException e) {
            return new ArrayList<>();
        }

        return orders;
    }
}

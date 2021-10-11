package View;

import BO.Status;
import BO.ItemHandler;
import BO.OrderHandler;
import BO.UserHandler;
import BO.Type;
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
        try {
            DBManager.getCon();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override // https://stackoverflow.com/questions/9500051/multiple-method-calling-using-single-servlet
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        if (req.getParameter("login") != null) {
            login(req, session);
        } else if (req.getParameter("register") != null) {
            registerUser(req, session);
        } else if (req.getParameter("logout") != null) {
            session.setAttribute("user", null);
        } else if (req.getParameter("itemId") != null) {
            addItemToCart(req, session);
        } else if (req.getParameter("clearCart") != null) {
            clearCart(session);
        } else if (req.getParameter("checkOut") != null) {
            if(!checkoutItems(session)){
                resp.sendRedirect(home + "cart.jsp");
                return;
            }
        } else if (req.getParameter("goToCart") != null) {
            if (goToCart(resp, session)) {
                return;
            }
        } else if (req.getParameter("removeId") != null) {
            removeItemFromCart(req, session);
        } else if (req.getParameter("updateUser") != null) {
            updateUser(req, session);
        } else if (req.getParameter("updateOrder") != null) {
            updateOrder(req, session);
        } else if(req.getParameter("goToItems") !=null) {
            if (goToItems(resp, session)) {
                return;
            }
        } else if (req.getParameter("updateItem")!=null) {
            updateItem(req, session);
            resp.sendRedirect(home + "item.jsp");
            return;
        } else if(req.getParameter("addItem") != null) {
            addNewItem(req, session);
            resp.sendRedirect(home + "item.jsp");
            return;
        } else if (req.getParameter("deleteItem")!= null) {
            deleteItem(req, session);
            resp.sendRedirect(home + "item.jsp");
            return;
        }
        resp.sendRedirect(home);
    }

    private void deleteItem(HttpServletRequest req, HttpSession session) {
        try {
            ItemHandler.deleteItem(req.getParameter("deleteItem"));
        } catch (SQLException e) {
            session.setAttribute("deleteError", "Could not delete item as it is in an existing order");
        }
    }

    private boolean goToItems(HttpServletResponse resp, HttpSession session) {
        UserInfo user = (UserInfo) session.getAttribute("user");
        if (user == null) {
            session.setAttribute("error", "You need to login");
            return false;
        } else {
            try {
                if (user.getType() == Type.ADMIN) {
                    resp.sendRedirect(home + "item.jsp");
                    return true;
                } else {
                    session.setAttribute("error", "Wrong access level");
                    return false;
                }
            } catch (IOException e) {
                session.setAttribute("error", "Internal server error");
                return false;
            }
        }
    }

    private void updateUser(HttpServletRequest req, HttpSession session) {
        int id = Integer.parseInt(req.getParameter("updateUser"));
        Type type = Type.valueOf(req.getParameter("roles"));
        if (!UserHandler.setType(id, type)) {
            session.setAttribute("errorAdmin", "Failed to update role");
        }
    }

    private void updateOrder(HttpServletRequest req, HttpSession session) {
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
    }


    private void addNewItem(HttpServletRequest req, HttpSession session) {
        String name = req.getParameter("newName");
        String type = req.getParameter("newType");
        int stock = Integer.parseInt(req.getParameter("newStock"));
        float price = Float.parseFloat(req.getParameter("newPrice"));

        try {
            ItemHandler.createItem(name,type,stock,price);
        } catch (SQLException e) {
            session.setAttribute("updateError","An internal server error occurred");
        }
    }

    private void updateItem(HttpServletRequest req, HttpSession session) {
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
    }

    private boolean checkoutItems(HttpSession session) {
        HashMap<Integer, Integer> cart;
        HashMap<Integer, ItemInfo> cartInfo;
        if (session.getAttribute("cart") != null && session.getAttribute("cartInfo") != null) {
            cart = (HashMap<Integer, Integer>) session.getAttribute("cart");
            cartInfo = (HashMap<Integer, ItemInfo>) session.getAttribute("cartInfo");

            try {
                OrderHandler.checkOut(cart,cartInfo);
                clearCart(session);
                return true;
            } catch (SQLException e) {
                session.setAttribute("errorCart", "Could not complete your order");
                return false;
            }
        }
        return false;
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

    private void clearCart(HttpSession session) {
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
        ItemInfo item = null;
        try {
            item = ItemHandler.getById(Integer.toString(id));
        } catch (SQLException e) {
            session.setAttribute("error", "Cannot find item");
        }
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
            UserHandler.createUser(username, password);
        } catch (SQLException e) {
            session.setAttribute("error", "That username already exists");
        }
    }

    private boolean authenticate(HttpServletRequest req, HttpSession session) {
        try {
            return UserHandler.login(req.getParameter("username"), req.getParameter("password"));

        } catch (SQLException | NullPointerException e) {
            session.setAttribute("error", "No such user");
            return false; // No such user
        }
    }


    public static HashMap<Integer, ItemInfo> getItems() {
        ArrayList<ItemInfo> items;
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
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return collection;
    }

    public void destroy() {
        DBManager.disconnect();
    }


    private void login(HttpServletRequest req, HttpSession session) {
        if (authenticate(req,session)) {
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
        ArrayList<OrderInfo> orders;
        try {
            orders = (ArrayList<OrderInfo>) OrderHandler.getAll();
        } catch (SQLException e) {
            return new ArrayList<>();
        }

        return orders;
    }

    private static boolean goToCart(HttpServletResponse resp, HttpSession session) {
        UserInfo user = (UserInfo) session.getAttribute("user");
        if (user == null) {
            session.setAttribute("error", "You need to login");
            return false;
        } else {
            try {
                if (user.getType() == Type.USER) {
                    resp.sendRedirect(home + "cart.jsp");
                    return true;
                } else {
                    session.setAttribute("error", "Wrong access level");
                    return false;
                }
            } catch (IOException e) {
                session.setAttribute("error", "Internal server error");
                return false;
            }
        }
    }
}

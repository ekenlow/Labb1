package View;

import BO.ItemHandler;
import BO.UserHandler;
import DB.DBManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
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
        RequestDispatcher dispatcher;
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
            //checkoutItems(req,session);
        } else if (req.getParameter("goToCart") != null) {
            resp.sendRedirect(home + "cart.jsp");
            return;
        } else if (req.getParameter("removeId") != null) {
            removeItemFromCart(req,session);
        }
        resp.sendRedirect(home);
    }

    private void removeItemFromCart(HttpServletRequest req, HttpSession session) {
        
    }

    private void clearCart(HttpServletRequest req, HttpSession session) {
        HashMap<Integer, Integer> cart;
        if (session.getAttribute("cart") == null) {
            session.setAttribute("error", "Cart already cleared");
        } else {
            cart = (HashMap<Integer, Integer>) session.getAttribute("cart");
            cart.clear();
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
        if(item.getStock() <= 0){
            session.setAttribute("error", "Item out of stock");
            return;
        }
        int amount = cart.get(id) == null ? 1 : cart.get(id) + 1;
        if(amount <= item.getStock()) {
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


    public static HashMap<Integer,ItemInfo> getItems() {
        ArrayList<ItemInfo> items = (ArrayList<ItemInfo>) ItemHandler.getAll();
        HashMap<Integer, ItemInfo> map = new HashMap<>();
        for (ItemInfo item : items) {
            map.put(item.getId(),item);
        }
        return map;
    }

    public void destroy() {
        DBManager.disconnect();
    }


    private void login(HttpServletRequest req, HttpServletResponse resp, HttpSession session) {
        if (authenticate(req, resp)) {
            RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
            try {
                UserInfo u = UserHandler.getByName(req.getParameter("username"));
                session.setAttribute("user", u);
                dispatcher.forward(req, resp);
            } catch (ServletException e) {
                System.err.println("Index.jsp not found");
                e.printStackTrace();
            } catch (Exception e) {
                session.setAttribute("error", "Internal server error");
            }
        } else {
            session.setAttribute("error", "Username or password incorrect");
        }
    }
}

package View;

import BO.ItemHandler;
import BO.UserHandler;
import DB.DBManager;
import com.mysql.cj.Session;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.ArrayList;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class Controller extends HttpServlet {

    private static final String home = "/Labb1_war_exploded/";

    public void init() {
        DBManager.getCon();
    }


    @Override // https://stackoverflow.com/questions/9500051/multiple-method-calling-using-single-servlet
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        RequestDispatcher dispatcher;
        System.out.println("HEREEEE");
        if (req.getParameter("action2") != null) {
            System.out.println("here 2");
            resp.sendRedirect(home);
        } else {
            System.err.println("Missning name in JSP");
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        HttpSession session = req.getSession();
        if (req.getParameter("login") != null) {
            if (authenticate(req, resp)) {
                dispatcher = req.getRequestDispatcher("index.jsp");
                try {
                    UserInfo u = UserHandler.getByName(req.getParameter("username"));
                    session.setAttribute("user", u);
                    dispatcher.forward(req, resp);
                } catch (ServletException e) {
                    System.err.println("Index.jsp not found");
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                session.setAttribute("error", "Username or password incorrect");
                resp.sendRedirect("/Labb1_war_exploded/");
            }
        } else if (req.getParameter("register") != null) {
            String username = req.getParameter("username");
            String password = req.getParameter("password");
            try {
                UserInfo u = UserHandler.createUser(username, password);
            } catch (SQLException e) {
                session.setAttribute("error", "That username already exists");
            }
            resp.sendRedirect(home);
        } else if (req.getParameter("logout") != null) {
            session.setAttribute("user", null);
            resp.sendRedirect(home);
        } else if (req.getParameter("addToCart") != null) {
            System.out.println("In add to cart");
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


    public static Collection<ItemInfo> getItems() {
        return ItemHandler.getAll();
    }

    public void destroy() {
        DBManager.disconnect();
    }
}

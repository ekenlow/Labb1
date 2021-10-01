package View;

import BO.ItemHandler;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "helloServlet", value="/hello-servlet")
public class Controller extends HttpServlet {
    private String message;

    public void init() {
        // Run before anything else.
    }


    public void getSodas(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        message =ItemHandler.getByName("Cola").toString();
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }


    @Override // https://stackoverflow.com/questions/9500051/multiple-method-calling-using-single-servlet
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       switch (req.getParameterNames().toString()) {
           case "action1":
               getSodas(req, resp);
               break;
           case "action2...":
               break;
           default:
               System.out.println("No action made");
               break;
       }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    public void destroy() {

    }
}

<%--
  Created by IntelliJ IDEA.
  User: oscar.ekenlow
  Date: 2021-10-01
  Time: 15:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="View.Controller" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
    </head>
    <body>
        <%
            String userEmail = request.getParameter("userEmail");
            String pswd = request.getParameter("pass");
            if (pswd != null && userEmail != null) {
                session.setAttribute("userEmail", userEmail); %>
                Logged in as user: <%= userEmail%>
        <% } else { %>
            <form method="post" action="welcome.jsp">
                <table border="1">
                    <tbody>
                        <tr>
                            <td>Email</td>
                            <td><input type="text" name="userEmail" value=""></td>
                        </tr>
                        <tr>
                            <td>Password</td>
                            <td><input type="password" name="pass" value=""></td>
                        </tr>
                    </tbody>
                </table>
                <input type="submit" value="login">
            </form>
        <% } %>
    </body>
</html>

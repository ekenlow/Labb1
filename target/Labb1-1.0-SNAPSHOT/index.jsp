<%@ page import="View.Controller" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Login!" %>
</h1>
<br/>
<form method="post" action="${pageContext.request.contextPath}/hello-servlet">
    <table>
        <tbody>
        <tr>
            <td><p>Email</p><input type="text" value="" name="username" id="username"></td>
        </tr>
        <tr>
            <td><p>Password</p><input type="password" value="" name="password" id="password"></td>
        </tr>
        </tbody>
    </table>
    <input type="submit" value="login" id="loginBtn" name="login"/>
    <input type="reset" value="reset" id="resetBtn" name="reset"/>
    <input type="submit" value="register" id="registerBtn" name="register"/>
</form>
<%
    String msg = (String) session.getAttribute("error");
    if (msg != null) {
        %><p style="color:red"><%= msg %></p><%
    } %>
</body>
</html>
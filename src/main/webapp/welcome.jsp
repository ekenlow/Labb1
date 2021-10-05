<%--
  Created by IntelliJ IDEA.
  User: evang
  Date: 2021-10-04
  Time: 15:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="View.Controller" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
    <h1>YOU ARE LOGGED IN!!!!!</h1>
    <p>Welcome ${sessionScope.get("user").username}, you are a ${sessionScope.get("user").type}</p>
</body>
</html>

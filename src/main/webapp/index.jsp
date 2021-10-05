<%@ page import="View.Controller" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<%if (session.getAttribute("user") == null){%>
    <%@ include file="login.jsp" %>
<%}else{%>
    <p>Welcome ${sessionScope.get("user").username}, you are a ${sessionScope.get("user").type}</p>
<% }%>
<% String msg = (String) session.getAttribute("error");
    if (msg != null) {%>
        <p style="color:red"><%= msg %></p>
<% } %>
</body>
</html>
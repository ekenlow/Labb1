<%@ page import="View.Controller" %>
<%@ page import="BO.Type" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="View.ItemInfo" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Webbshop</title>
</head>
<style>
    table, th, td {
        border:1px solid black;
    }
</style>
<body>
<% ArrayList<View.ItemInfo> items = (ArrayList<ItemInfo>) Controller.getItems();
    session.setAttribute("items", items);
%>
<%if (session.getAttribute("user") == null){%>
    <%@ include file="login.jsp" %>
<%}else{%>

<form method="post" action="${pageContext.request.contextPath}/hello-servlet">
    <p>Welcome ${sessionScope.get("user").username}, you are a ${sessionScope.get("user").type}
        <input type="submit" value="logout" name="logout">
    </p>

</form>
<% }%>
<% String msg = (String) session.getAttribute("error");
    if (msg != null) {%>
        <p style="color:red"><%= msg %></p>
<%
    session.setAttribute("error", null);
} %>
<br>
<table>
    <c:if test="${sessionScope.user == null || sessionScope.user.type == Type.USER}">
        <tr>
            <th>Product name</th>
            <th>Price</th>
            <th>Stock</th>
        </tr>
    
            <c:forEach var="item" items="${sessionScope.items}">
                    <tr>
                            <td><c:out value="${item.name}"></c:out></td>
                            <td><c:out value="${item.price}"></c:out></td>
                            <td><c:out value="${item.stock}"></c:out></td> <!-- //Lagrar vi id i knappen? När tar vi från lagret? Vid checkout? -->
                            <td><input type="submit" id="addCartBtn" name="addToCart" value="${item.id}"/></td>
                    </tr>
            </c:forEach>

    </c:if>
</table>
</body>
</html>
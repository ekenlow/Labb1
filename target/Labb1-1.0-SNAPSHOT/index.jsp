<%@ page import="View.Controller" %>
<%@ page import="BO.Type" %>
<%@ page import="java.util.HashMap" %>
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
<c:if test="${sessionScope.user == null || sessionScope.user.type == Type.USER}">
    <% HashMap<Integer, ItemInfo> items = Controller.getItems();
        session.setAttribute("items", items);
        if(items.isEmpty()){
    %>
    <p style="color:red"><%= "Internal Server Error" %></p>
    <%}%>
    <table>
        <tr>
            <th>Product name</th>
            <th>Price</th>
            <th>Stock</th>
            <th>In cart</th>
        </tr>

            <c:forEach var="item" items="${sessionScope.items}">
                    <tr>
                            <td><c:out value="${item.value.name}"></c:out></td>
                            <td><c:out value="${item.value.price}"></c:out>:-</td>
                            <td><c:out value="${item.value.stock}"></c:out></td> <!-- //Lagrar vi id i knappen? När tar vi från lagret? Vid checkout? -->
                            <td>
                                <form action="${pageContext.request.contextPath}/hello-servlet" method="post">
                                    <input type="hidden" name="itemId" value="${item.key}" />
                                    <input type="submit" value="Add to cart" />
                                </form>
                            </td>
                        <td>
                            <c:out value="${sessionScope.cart[item.key]}">0</c:out>
                        </td>
                    </tr>
            </c:forEach>
    </table>
<a href="${pageContext.request.contextPath}/hello-servlet?goToCart">Go to cart</a>
</c:if>

<c:if test="${sessionScope.user.type == Type.ADMIN}">
    <a href="${pageContext.request.contextPath}/hello-servlet?goToItems">Go to items</a>
    <%@include file="users.jsp"%>
</c:if>

<c:if test="${sessionScope.user.type == Type.STOCKFILLER}">
    <%@include file="order.jsp"%>
</c:if>
<br>
</body>
</html>
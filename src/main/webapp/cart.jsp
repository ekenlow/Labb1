<%@ page import="View.Controller" %>
<%@ page import="java.util.HashMap" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<style>
    table, th, td {
        border: 1px solid black;
    }
</style>
<head>
    <title>Cart</title>
</head>
<body>
<h1>Cart</h1>
<% HashMap<Integer, Integer> cart = (HashMap<Integer, Integer>) session.getAttribute("cart");
    if ((cart == null) || (cart.size() == 0)) {%>
<p>Cart is empty</p>
<% } else { %>
<table>
    <tr>
        <th>Amount</th>
        <th>Item</th>
        <th>Price</th>
    </tr>
    <c:set var="totalPrice" value="0"/>
    <c:forEach var="item" items="${sessionScope.cart}">
        <c:set var="price" value="${sessionScope.items[item.key].price * sessionScope.cart[item.key]}"/>
        <c:set var="totalPrice" value="${totalPrice + price}"/>
        <tr>
            <td><c:out value="${cart[item.key]}"></c:out></td>
            <td><c:out value="${sessionScope.items[item.key].name}"></c:out></td>
            <td><c:out value="${price}"></c:out></td>
            <td>
                <form action="${pageContext.request.contextPath}/hello-servlet" method="post">
                    <input type="hidden" name="removeId" value="${item.key}"/>
                    <input type="submit" value="Remove from cart"/>
                </form>
            </td>
        </tr>
    </c:forEach>
    <tr><!--TODO:Fixa med en if sats så ifall cart är tom så ska denna row inte visas, eller helst hela tabellen!-->
        <th>Total price</th>
        <th><c:out value="${totalPrice}"></c:out></th>
        <td>SEK</td>
        <td>
            <form action="${pageContext.request.contextPath}/hello-servlet" method="post">
                <input type="hidden" name="checkOut"/>
                <input type="submit" value="Check out"/>
            </form>
        </td>
    </tr>
</table>
<% } %>
<br>
<a href="index.jsp">Go to home page</a>
</body>
</html>

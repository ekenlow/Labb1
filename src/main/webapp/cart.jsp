<%@ page import="View.Controller" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<style>
    table, th, td {
        border:1px solid black;
    }
</style>
<head>
    <title>Cart</title>
</head>
<body>
<h1>Cart</h1>
<c:if test="empty ${sessionScope.cart}">
    <p>Cart is empty</p>
</c:if>
<table>
    <tr>
        <th>Amount</th>
        <th>Item</th>
        <th>Price</th>
    </tr>
    <c:forEach var="item" items="${sessionScope.cart}">
        <c:set var="price" value="${sessionScope.items[item.key].price * sessionScope.cart[item.key]}"/>
        <tr>
            <td><c:out value="${cart[item.key]}"></c:out></td>
            <td><c:out value="${sessionScope.items[item.key].name}"></c:out></td>
            <td><c:out value="${price}"></c:out></td>
            <td>
                <form action="${pageContext.request.contextPath}/hello-servlet" method="post">
                    <input type="hidden" name="removeId" value="${item.key}" />
                    <input type="submit" value="Remove from cart" />
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<br>
<a href="index.jsp">Go to home page</a>
</body>
</html>

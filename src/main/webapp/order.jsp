<%@ page import="View.Controller" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="View.OrderInfo" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% ArrayList<OrderInfo> orders = Controller.getOrders();
    session.setAttribute("orders", orders);
%>

<% String msgOrder = (String) session.getAttribute("errorOrder");
    if (msgOrder != null) {%>
<p style="color:red"><%= msgOrder %></p>
<%
        session.setAttribute("errorOrder", null);
    } %>


<table>
    <thead>
    <tr>
        <th>Order ID</th>
        <th>Status</th>
        <th>Items</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="order" items="${sessionScope.orders}">
        <tr>
            <td>${order.id}</td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/hello-servlet">
                    <input type="hidden" name="updateOrder" value="${order.id}">
                    <input type="submit" value="${order.status}">
                </form>
            </td>
            <td>
                <table>
                    <thead>
                    <tr>
                        <th>Product name</th>
                        <th>Count</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="item" items="${order.items}">
                        <tr>
                            <td>${item.name}</td>
                            <td>${order.counts.get(item.id)}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
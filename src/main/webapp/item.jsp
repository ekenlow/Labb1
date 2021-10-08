<%@ page import="View.ItemInfo" %>
<%@ page import="View.Controller" %>
<%@ page import="java.util.HashMap" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>Items</title>
</head>
<style>
    table, th, td {
        border: 1px solid black;
    }
</style>
<body>
Hello world!

<% HashMap<Integer, ItemInfo> items = Controller.getItems();
    session.setAttribute("items", items);
%>
<% String msg = (String) session.getAttribute("deleteError");
    if (msg != null) {%>
<p style="color:red"><%= msg %></p>
<%
        session.setAttribute("deleteError", null);
    } %>

<table>
    <tr>
        <th>Product name</th>
        <th>Category</th>
        <th>Price</th>
        <th>Stock</th>
    </tr>
    <c:forEach var="item" items="${sessionScope.items}">

            <tr>
                <form action="${pageContext.request.contextPath}/hello-servlet" method="post">
                    <td><input type="text" value="${item.value.name}" name="name" id="name" size="10"></td>
                    <td><input type="text" value="${item.value.type}" name="type" id="type" size="10"></td>
                    <td><input type="text" value="${item.value.price}" name="price" id="price" size="10"></td>
                    <td><input type="text" value="${item.value.stock}" name="stock" id="stock" size="10"></td>
                    <td>
                            <input type="hidden" name="updateItem" value="${item.key}"/>
                            <input type="submit" value="Update"/>
                    </td>
                </form>
                <td>
                    <form action="${pageContext.request.contextPath}/hello-servlet" method="post">
                        <input type="hidden" name="deleteItem" value="${item.key}"/>
                        <input type="submit" value="Delete"/>
                    </form>
                </td>
            </tr>
    </c:forEach>
</table>

<form action="${pageContext.request.contextPath}/hello-servlet" method="post">
    <table>
        <h4>Add Item</h4>
        <tr>
            <td><label>Name: </label><input type="text" name="newName" value=""></td>
            <td><label>Price: </label><input type="text" name="newPrice" value=""></td>
            <td><label>Type: </label><input type="text" name="newType" value=""></td>
            <td><label>Stock: </label><input type="text" name="newStock" value=""></td>
            <td>
                <input type="submit" name="addItem" value="Create new Item">

            </td>
        </tr>
    </table>
</form>

</body>
</html>

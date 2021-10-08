<%@page import="BO.Type" %>
<%@page import="View.Controller" %>
<%@page import="View.UserInfo" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<% ArrayList<UserInfo> users = Controller.getUsers();
    session.setAttribute("users", users);
%>

<form method="post" action="${pageContext.request.contextPath}/hello-servlet">
    <table>
        <thead>
        <tr>
            <th>Username</th>
            <th>Role</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach var="user" items="${sessionScope.users}">
                <tr>
                    <td><c:out value="${user.username}"></c:out></td>
                    <td>
                       <form action="${pageContext.request.contextPath}/hello-servlet" method="post">
                           <select name="roles" id="roles">
                               <c:forEach items="${Type.values()}" var="type">
                                   <option value="${type}" ${type == user.type ? 'selected="selected"':''}> ${type} </option>
                               </c:forEach>
                           </select>
                           <input type="hidden" name="updateUser" value="${user.id}">
                           <input type="submit" name="submit">
                       </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

</form>
<% String msgAdmin = (String) session.getAttribute("errorAdmin");
    if (msgAdmin != null) {%>
<p style="color:red"><%= msgAdmin %></p>
<%
        session.setAttribute("errorAdmin", null);
    } %>
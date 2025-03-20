<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Manage Restaurants</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<h2>Manage Restaurants</h2>

<a href="/admin/restaurants/add">Add New Restaurant</a>

<c:if test="${not empty restaurants}">
    <table border="1">
        <tr>
            <th>Name</th>
            <th>Address</th>
            <th>Contact</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="restaurant" items="${restaurants}">
            <tr>
                <td><a href="/admin/restaurants/${restaurant.restaurantId}/dishes">${restaurant.name}</a></td>
                <td>${restaurant.address}</td>
                <td>${restaurant.contactNumber}</td>
                <td>
                    <a href="/admin/restaurants/edit/${restaurant.restaurantId}">Edit</a> |
                    <a href="/admin/restaurants/delete/${restaurant.restaurantId}" onclick="return confirm('Are you sure?');">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${empty restaurants}">
    <p>No restaurants found.</p>
</c:if>

</body>
</html>

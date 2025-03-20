<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Manage Dishes</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<h2>Manage Dishes</h2>

<a href="/admin/restaurants/${restaurantId}/dishes/add">Add New Dish</a>

<c:if test="${not empty dishes}">
    <table border="1">
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Price</th>
            <th>Type</th>
            <th>Size</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="dish" items="${dishes}">
            <tr>
                <td>${dish.name}</td>
                <td>${dish.description}</td>
                <td>${dish.price}</td>
                <td>${dish.dishType}</td>
                <td>${dish.dishSize}</td>
                <td>
                    <a href="/admin/restaurants/${restaurantId}/dishes/edit/${dish.dishId}">Edit</a> |
                    <a href="/admin/restaurants/${restaurantId}/dishes/delete/${dish.dishId}" onclick="return confirm('Are you sure?');">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${empty dishes}">
    <p>No dishes found.</p>
</c:if>

<a href="/admin/restaurants">Back to Restaurants</a>
</body>
</html>

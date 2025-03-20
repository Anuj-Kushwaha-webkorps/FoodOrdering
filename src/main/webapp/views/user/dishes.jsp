<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${restaurant.name} - Menu</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<h2>Menu - ${restaurant.name}</h2>

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
                    <a href="/user/cart/add/${dish.dishId}">Add to Cart</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${empty dishes}">
    <p>No dishes available at this restaurant.</p>
</c:if>

<a href="/user/restaurants">Back to Restaurants</a>
</body>
</html>

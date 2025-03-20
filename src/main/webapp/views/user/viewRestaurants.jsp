<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Available Restaurants</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<h2>Available Restaurants</h2>

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
                <td>${restaurant.name}</td>
                <td>${restaurant.address}</td>
                <td>${restaurant.contactNumber}</td>
                <td>
                    <a href="/user/restaurants/${restaurant.restaurantId}/dishes">View Menu</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${empty restaurants}">
    <p>No restaurants available at the moment.</p>
</c:if>

<a href="/user/dashboard">Back to Dashboard</a>
</body>
</html>

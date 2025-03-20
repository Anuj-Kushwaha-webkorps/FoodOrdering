<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Edit Restaurant</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<h2>Edit Restaurant</h2>

<form action="/admin/restaurants/edit" method="post">
    <input type="hidden" name="restaurantId" value="${restaurant.restaurantId}">

    <label for="name">Restaurant Name:</label>
    <input type="text" id="name" name="name" value="${restaurant.name}" required><br>

    <label for="address">Address:</label>
    <textarea id="address" name="address" required>${restaurant.address}</textarea><br>

    <label for="contact">Contact Number:</label>
    <input type="text" id="contact" name="contact" value="${restaurant.contactNumber}" required><br>

    <button type="submit">Update Restaurant</button>
</form>

<a href="/admin/restaurants">Back to Restaurant List</a>
</body>
</html>
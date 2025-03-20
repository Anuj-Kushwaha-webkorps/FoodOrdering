<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Add Restaurant</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<h2>Add New Restaurant</h2>

<form action="/admin/restaurants/add" method="post">
    <label for="name">Restaurant Name:</label>
    <input type="text" id="name" name="name" required><br>

    <label for="address">Address:</label>
    <textarea id="address" name="address" required></textarea><br>

    <label for="contact">Contact Number:</label>
    <input type="text" id="contact" name="contact" required><br>

    <button type="submit">Add Restaurant</button>
</form>

<a href="/admin/restaurants">Back to Restaurant List</a>
</body>
</html>
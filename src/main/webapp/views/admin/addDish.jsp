<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Add Dish</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<h2>Add New Dish</h2>

<form action="/admin/restaurants/${restaurantId}/dishes/add" method="post">
    <label for="name">Dish Name:</label>
    <input type="text" id="name" name="name" required><br>

    <label for="description">Description:</label>
    <textarea id="description" name="description"></textarea><br>

    <label for="price">Price:</label>
    <input type="number" id="price" name="price" step="0.01" required><br>

    <label for="dishType">Type:</label>
    <select id="dishType" name="dishType" required>
        <option value="VEG">Veg</option>
        <option value="NON_VEG">Non-Veg</option>
    </select><br>

    <label for="dishSize">Size:</label>
    <select id="dishSize" name="dishSize" required>
        <option value="HALF">Half Plate</option>
        <option value="FULL">Full Plate</option>
    </select><br>

    <button type="submit">Add Dish</button>
</form>

<a href="/admin/restaurants/${restaurantId}/dishes">Back to Dishes</a>
</body>
</html>
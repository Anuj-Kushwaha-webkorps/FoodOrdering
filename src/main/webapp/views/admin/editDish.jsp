<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Edit Dish</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<h2>Edit Dish</h2>

<form action="/admin/restaurants/${restaurantId}/dishes/edit" method="post">
    <input type="hidden" name="dishId" value="${dish.dishId}">

    <label for="name">Dish Name:</label>
    <input type="text" id="name" name="name" value="${dish.name}" required><br>

    <label for="description">Description:</label>
    <textarea id="description" name="description">${dish.description}</textarea><br>

    <label for="price">Price:</label>
    <input type="number" id="price" name="price" value="${dish.price}" step="0.01" required><br>

    <label for="dishType">Type:</label>
    <select id="dishType" name="dishType" required>
        <option value="VEG" ${dish.dishType == 'VEG' ? 'selected' : ''}>Veg</option>
        <option value="NON_VEG" ${dish.dishType == 'NON_VEG' ? 'selected' : ''}>Non-Veg</option>
    </select><br>

    <label for="dishSize">Size:</label>
    <select id="dishSize" name="dishSize" required>
        <option value="HALF" ${dish.dishSize == 'HALF' ? 'selected' : ''}>Half Plate</option>
        <option value="FULL" ${dish.dishSize == 'FULL' ? 'selected' : ''}>Full Plate</option>
    </select><br>

    <button type="submit">Update Dish</button>
</form>

<a href="/admin/restaurants/${restaurantId}/dishes">Back to Dishes</a>
</body>
</html>
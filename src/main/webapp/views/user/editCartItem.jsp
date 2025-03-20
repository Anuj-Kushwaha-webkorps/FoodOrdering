<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Edit Cart Item</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<h2>Edit Cart Item</h2>

<form action="/user/cart/update" method="post">
    <input type="hidden" name="dishId" value="${cartItem.dishId}">
    
    <label for="quantity">Quantity:</label>
    <input type="number" id="quantity" name="quantity" value="${cartItem.quantity}" min="1" required><br>

    <label for="dishSize">Size:</label>
    <select id="dishSize" name="dishSize" required>
        <option value="HALF" ${cartItem.dishSize == 'HALF' ? 'selected' : ''}>Half Plate</option>
        <option value="FULL" ${cartItem.dishSize == 'FULL' ? 'selected' : ''}>Full Plate</option>
    </select><br>

    <button type="submit">Update</button>
</form>

<a href="/user/cart/view">Back to Cart</a>
</body>
</html>
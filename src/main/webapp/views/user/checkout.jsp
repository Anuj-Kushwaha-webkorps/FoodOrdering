<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Checkout</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<h2>Checkout</h2>

<table border="1">
    <tr>
        <th>Name</th>
        <th>Size</th>
        <th>Quantity</th>
        <th>Price</th>
        <th>Subtotal</th>
    </tr>
    <c:forEach var="item" items="${cart}">
        <tr>
            <td>${item.dishName}</td>
            <td>${item.dishSize}</td>
            <td>${item.quantity}</td>
            <td>${item.price}</td>
            <td>${item.price * item.quantity}</td>
        </tr>
    </c:forEach>
</table>

<h3>Total Price: ${totalPrice}</h3>

<form action="/user/cart/confirm-order" method="post">
    <label>Delivery Address:</label>
    <textarea name="address" required></textarea>
    <br>
    <button type="submit">Place Order</button>
</form>

<a href="/user/cart/view">Back to Cart</a>
</body>
</html>

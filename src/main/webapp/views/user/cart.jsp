<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Shopping Cart</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<h2>Your Cart</h2>

<c:if test="${not empty cart}">
    <table border="1">
        <tr>
            <th>Name</th>
            <th>Price</th>
            <th>Size</th>
            <th>Quantity</th>
            <th>Subtotal</th>
            <th>Actions</th>
        </tr>
        <c:set var="totalPrice" value="0" />
        <c:forEach var="item" items="${cart}">
            <c:set var="subtotal" value="${item.price * item.quantity}" />
            <c:set var="totalPrice" value="${totalPrice + subtotal}" />
            <tr>
                <td>${item.dishName}</td>
                <td>${item.price}</td>
                <td>${item.dishSize}</td>
                <td>${item.quantity}</td>
                <td>${subtotal}</td>
                <td>
                    <a href="/user/cart/remove/${item.dishId}">Remove</a> |
                    <a href="/user/cart/edit/${item.dishId}">Edit</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <h3>Total Price: ${totalPrice}</h3>
    <br>
    <a href="/user/cart/checkout">Proceed to Checkout</a>
</c:if>

<c:if test="${empty cart}">
    <p>Your cart is empty.</p>
</c:if>

<a href="/user/restaurants">Back to Restaurants</a>
</body>
</html>
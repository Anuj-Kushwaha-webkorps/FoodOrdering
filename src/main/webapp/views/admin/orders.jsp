<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Manage Orders</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<h2>Manage Orders</h2>

<c:if test="${not empty orders}">
    <table border="1">
        <tr>
            <th>Order ID</th>
            <th>User</th>
            <th>Dishes</th>
            <th>Total Price</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="order" items="${orders}">
            <tr>
                <td>${order.orderId}</td>
                <td>${order.user.name}</td>
                <td>
                    <ul>
                        <c:forEach var="item" items="${order.items}">
                            <li>${item.dishName} - ${item.quantity} x ${item.pricePerItem} (${item.dish.dishSize})</li>
                        </c:forEach>
                    </ul>
                </td>
                <td>${order.totalPrice}</td>
                <td>${order.status}</td>
                <td>
                    <c:if test="${order.status == 'PENDING'}">
                        <form action="/admin/orders/accept/${order.orderId}" method="post">
                            <button type="submit">Accept</button>
                        </form>
                        <form action="/admin/orders/reject/${order.orderId}" method="post">
                            <button type="submit">Reject</button>
                        </form>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${empty orders}">
    <p>No orders available.</p>
</c:if>

<a href="/admin/dashboard">Back to Dashboard</a>
</body>
</html>

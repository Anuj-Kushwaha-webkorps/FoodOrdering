<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Order History</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<h2>Order History</h2>

<c:if test="${not empty pastOrders}">
    <table border="1">
        <tr>
            <th>Order ID</th>
            <th>User</th>
            <th>Dishes</th>
            <th>Total Price</th>
            <th>Status</th>
            <th>Date</th>
			<th>Action</th>
        </tr>
        <c:forEach var="order" items="${pastOrders}">
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
                <td>${order.orderTime}</td>
				<td>
					<c:if test="${order.status == 'ACCEPTED' || order.status == 'COMPLETED'}">
					                <form action="/admin/orders/send-receipt/${order.orderId}" method="post">
					                    <input type="hidden" name="recipientEmail" value="${order.user.email}">
					                    <button type="submit">Send Email</button>
					                </form>
					</c:if>
				</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${empty pastOrders}">
    <p>No past orders found.</p>
</c:if>

<a href="/admin/dashboard">Back to Dashboard</a>
</body>
</html>

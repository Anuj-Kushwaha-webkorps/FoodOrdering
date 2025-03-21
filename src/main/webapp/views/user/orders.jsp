<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Your Orders</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<h2>Your Orders</h2>

<c:if test="${not empty orders}">
    <table border="1">
        <tr>
            <th>Order ID</th>
            <th>Dishes</th>
            <th>Total Price</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="order" items="${orders}">
            <tr>
                <td>${order.orderId}</td>
                <td>
                    <ul>
                        <c:forEach var="item" items="${order.items}">
                            <li>Name : ${item.dishName} <br> Quantity - ${item.quantity} <br> PricePerItem - ${item.pricePerItem} <br> DishSize - (${item.dish.dishSize})</li>
                        </c:forEach>
                    </ul>
                </td>
                <td>${order.totalPrice}</td>
                <td>${order.status}</td>
                <td>
                    <c:if test="${order.status == 'PENDING'}">
                        <form action="/user/orders/cancel/${order.orderId}" method="post">
                            <button type="submit">Cancel</button>
                        </form>
                    </c:if>
					<c:if test="${order.status == 'ACCEPTED' || order.status == 'COMPLETED'}">
					               <a href="/user/orders/download-receipt/${order.orderId}">
					                   <button>Download Receipt</button>
					               </a>
					 </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${empty orders}">
    <p>You have no active orders.</p>
</c:if>

<a href="/user/dashboard">Back to Dashboard</a>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Manage Orders</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<style>
	body {
	    font-family: Arial, sans-serif;
	    background-color: #f4f4f4;
	    margin: 0;
	    padding: 20px;
	    text-align: center;
	}

	h2 {
	    background-color: #007bff;
	    color: white;
	    padding: 15px;
	    border-radius: 5px;
	    display: inline-block;
	}

	table {
	    width: 90%;
	    margin: 20px auto;
	    border-collapse: collapse;
	    background: white;
	    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
	}

	th, td {
	    border: 1px solid #ddd;
	    padding: 12px;
	    text-align: center;
	}

	th {
	    background-color: #007bff;
	    color: white;
	}

	td ul {
	    padding: 0;
	    margin: 0;
	    list-style-type: none;
	}

	td ul li {
	    text-align: left;
	}

	button {
	    background-color: #28a745;
	    color: white;
	    padding: 8px 12px;
	    border: none;
	    border-radius: 5px;
	    cursor: pointer;
	}

	button:hover {
	    background-color: #218838;
	}

	button.reject {
	    background-color: #dc3545;
	}

	button.reject:hover {
	    background-color: #c82333;
	}

	a {
	    display: inline-block;
	    margin-top: 20px;
	    padding: 10px 15px;
	    background-color: #007bff;
	    color: white;
	    text-decoration: none;
	    border-radius: 5px;
	}

	a:hover {
	    background-color: #0056b3;
	}

	@media (max-width: 768px) {
	    table {
	        width: 100%;
	    }

	    button, a {
	        font-size: 14px;
	        padding: 8px 12px;
	    }
	}

</style>
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
			<c:if test="${!(order.status== 'CANCELED' || order.status == 'COMPLETED' || order.status == 'REJECTED')}">
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
			</c:if>
        </c:forEach>
    </table>
</c:if>

<c:if test="${empty orders}">
    <p>No orders available.</p>
</c:if>

<a href="/admin/dashboard">Back to Dashboard</a>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Order History</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<style>
	body {
	    font-family: Arial, sans-serif;
	    background-color: #f4f4f4;
	    margin: 0;
	    padding: 0;
	    text-align: center;
	}

	h2 {
	    background-color: #007bff;
	    color: white;
	    padding: 15px;
	    margin: 0;
	}

	table {
	    width: 90%;
	    max-width: 900px;
	    margin: 20px auto;
	    border-collapse: collapse;
	    background: white;
	    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	    border-radius: 8px;
	    overflow: hidden;
	}

	th, td {
	    border: 1px solid #ddd;
	    padding: 12px;
	    text-align: left;
	}

	th {
	    background-color: #007bff;
	    color: white;
	}

	td ul {
	    padding: 0;
	    list-style: none;
	}

	td ul li {
	    margin-bottom: 5px;
	}

	p {
	    font-size: 18px;
	    color: #555;
	}

	a {
	    display: inline-block;
	    margin: 20px 0;
	    text-decoration: none;
	    background-color: #007bff;
	    color: white;
	    padding: 10px 15px;
	    border-radius: 5px;
	    transition: background 0.3s ease;
	}

	a:hover {
	    background-color: #0056b3;
	}

	@media (max-width: 600px) {
	    table {
	        width: 100%;
	    }

	    th, td {
	        padding: 8px;
	    }
	}

</style>
<body>
<h2>Order History</h2>

<c:if test="${not empty pastOrders}">
    <table border="1">
        <tr>
            <th>Order ID</th>
            <th>Dishes</th>
            <th>Total Price</th>
            <th>Status</th>
            <th>Date</th>
        </tr>
        <c:forEach var="order" items="${pastOrders}">
            <tr>
                <td>${order.orderId}</td>
                <td>
                    <ul>
                        <c:forEach var="item" items="${order.items}">
                            <li>Name : ${item.dishName}<br> Quantity : ${item.quantity} <br> PricePerItem : ${item.pricePerItem} <br> DishType : (${item.dish.dishSize})</li>
                        </c:forEach>
                    </ul>
                </td>
                <td>${order.totalPrice}</td>
                <td>${order.status}</td>
                <td>${order.orderTime}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${empty pastOrders}">
    <p>No past orders found.</p>
</c:if>

<a href="/user/dashboard">Back to Dashboard</a>
</body>
</html>

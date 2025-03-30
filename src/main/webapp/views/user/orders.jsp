<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Your Orders</title>
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

	button {
	    background-color: #dc3545;
	    color: white;
	    border: none;
	    padding: 8px 12px;
	    font-size: 14px;
	    cursor: pointer;
	    border-radius: 5px;
	    transition: background 0.3s ease;
	}

	button:hover {
	    background-color: #c82333;
	}

	a button {
	    background-color: #28a745;
	}

	a button:hover {
	    background-color: #218838;
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

	    button {
	        width: 100%;
	        padding: 10px;
	    }
	}

</style>
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

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
	    background-color: #f8f9fa;
	    margin: 0;
	    padding: 20px;
	    text-align: center;
	}

	h2 {
	    background-color: #007bff;
	    color: white;
	    padding: 15px;
	    margin: 0;
	    font-size: 24px;
	    border-radius: 5px;
	}

	table {
	    width: 90%;
	    margin: 20px auto;
	    border-collapse: collapse;
	    background: white;
	    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
	}

	th, td {
	    border: 1px solid #ccc;
	    padding: 10px;
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
	    font-size: 14px;
	    cursor: pointer;
	}

	button:hover {
	    background-color: #218838;
	}

	a {
	    display: inline-block;
	    margin-top: 20px;
	    padding: 10px 15px;
	    background-color: #dc3545;
	    color: white;
	    text-decoration: none;
	    border-radius: 5px;
	    font-size: 16px;
	}

	a:hover {
	    background-color: #c82333;
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

	<script>
		<% 
		  String error = request.getParameter("error");
		  System.out.println(error);
		  if (error != null) { 
		  %>
		      alert("<%= error %>"); 
		  <% } %>
		  
		  <% 
	  		  String success = request.getParameter("success");
	  		  System.out.println(success);
	  		  if (success != null) { 
	  		  %>
	  		      alert("<%= success %>"); 
	  	<% } %>
	</script>

</body>
</html>

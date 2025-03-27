<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Shopping Cart</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<style>

	body {
	    font-family: Arial, sans-serif;
	    background-color: #f8f9fa;
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
	    max-width: 800px;
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
	    text-align: center;
	}

	th {
	    background-color: #007bff;
	    color: white;
	}

	h3 {
	    margin-top: 20px;
	    font-size: 22px;
	    color: #333;
	}

	a {
	    display: inline-block;
	    text-decoration: none;
	    padding: 8px 15px;
	    border-radius: 5px;
	    font-size: 14px;
	    transition: background 0.3s ease;
	}

	a[href*="remove"] {
	    background-color: #dc3545;
	    color: white;
	}

	a[href*="remove"]:hover {
	    background-color: #c82333;
	}

	a[href*="edit"] {
	    background-color: #ffc107;
	    color: black;
	}

	a[href*="edit"]:hover {
	    background-color: #e0a800;
	}

	a[href*="checkout"] {
	    background-color: #28a745;
	    color: white;
	    font-size: 16px;
	    padding: 10px 20px;
	    margin-top: 20px;
	}

	a[href*="checkout"]:hover {
	    background-color: #218838;
	}

	a[href*="restaurants"] {
	    background-color: #007bff;
	    color: white;
	    font-size: 14px;
	    padding: 10px 15px;
	    margin-top: 20px;
	}
	
	a[href*="dashboard"] {
		    background-color: #007bff;
		    color: white;
		    font-size: 14px;
		    padding: 10px 15px;
		    margin-top: 20px;
	}

	a[href*="restaurants"]:hover {
	    background-color: #0056b3;
	}
	
	a[href*="dashboard"]:hover {
		    background-color: #0056b3;
	}

	@media (max-width: 600px) {
	    table {
	        width: 100%;
	    }

	    th, td {
	        padding: 8px;
	    }

	    a {
	        display: block;
	        margin: 5px 0;
	    }
	}

</style>
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
<a href="/user/dashboard">Back to Dashboard</a>

<script>
	<% 
	  String error = request.getParameter("error");
	  System.out.println(error);
	  if (error != null) { 
	  %>
	      alert("<%= error %>"); 
	<% } %>

</script>
</body>
</html>
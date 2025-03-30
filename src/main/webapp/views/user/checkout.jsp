<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Checkout</title>
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

	form {
	    margin-top: 20px;
	}

	textarea {
	    width: 90%;
	    max-width: 600px;
	    height: 80px;
	    padding: 10px;
	    border-radius: 5px;
	    border: 1px solid #ccc;
	    font-size: 16px;
	}

	button {
	    background-color: #28a745;
	    color: white;
	    padding: 10px 15px;
	    font-size: 16px;
	    border: none;
	    cursor: pointer;
	    border-radius: 5px;
	    margin-top: 10px;
	    transition: background 0.3s ease;
	}

	button:hover {
	    background-color: #218838;
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

	    textarea {
	        width: 100%;
	    }
	}

</style>
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

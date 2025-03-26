<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Edit Cart Item</title>
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

	form {
	    background: white;
	    max-width: 400px;
	    margin: 20px auto;
	    padding: 20px;
	    border-radius: 8px;
	    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	    text-align: left;
	}

	label {
	    font-weight: bold;
	    display: block;
	    margin-top: 10px;
	}

	input, select {
	    width: 100%;
	    padding: 8px;
	    margin-top: 5px;
	    border: 1px solid #ccc;
	    border-radius: 5px;
	}

	button {
	    display: block;
	    width: 100%;
	    padding: 10px;
	    margin-top: 15px;
	    border: none;
	    background-color: #28a745;
	    color: white;
	    font-size: 16px;
	    border-radius: 5px;
	    cursor: pointer;
	    transition: background 0.3s ease;
	}

	button:hover {
	    background-color: #218838;
	}

	a {
	    display: inline-block;
	    margin-top: 20px;
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
	    form {
	        width: 90%;
	    }
	}

</style>
<body>
<h2>Edit Cart Item</h2>

<form action="/user/cart/update" method="post">
    <input type="hidden" name="dishId" value="${cartItem.dishId}">
    
    <label for="quantity">Quantity:</label>
    <input type="number" id="quantity" name="quantity" value="${cartItem.quantity}" min="1" required><br>

    <button type="submit">Update</button>
</form>

<a href="/user/cart/view">Back to Cart</a>
</body>
</html>
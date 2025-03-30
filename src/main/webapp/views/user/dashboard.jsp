<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Dashboard</title>
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

	ul {
	    list-style: none;
	    padding: 20px;
	    margin: 0 auto;
	    max-width: 300px;
	}

	ul li {
	    margin: 10px 0;
	}

	ul li a {
	    display: block;
	    text-decoration: none;
	    background-color: #28a745;
	    color: white;
	    padding: 10px;
	    border-radius: 5px;
	    transition: 0.3s ease;
	}

	ul li a:hover {
	    background-color: #218838;
	}

	.notification {
			    background: red;
			    color: white;
			    padding: 5px 8px;
			    border-radius: 50%;
			    font-size: 14px;
			    margin-left: 8px;
			}
	@media (max-width: 600px) {
	    ul {
	        max-width: 90%;
	    }
	}

	</style>
<body>
<h2>Welcome, ${user.name}</h2>

<ul>
    <li><a href="/user/restaurants">View Available Restaurants</a></li>
	<li><a href="/user/cart/view">Cart</a></li>
    <li><a href="/user/orders">My Orders <Strong class="notification"> ${notification} </Strong></a></li>
    <li><a href="/user/orders/history">Order History</a></li>
    <li><a href="/user/logout">Logout</a></li>
</ul>

</body>
</html>
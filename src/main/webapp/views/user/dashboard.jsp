<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>User Dashboard</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<h2>Welcome, ${user.name}</h2>

<ul>
    <li><a href="/user/restaurants">View Available Restaurants</a></li>
	<li><a href="/user/cart/view">Cart</li>
    <li><a href="/user/orders">My Orders</a></li>
    <li><a href="/user/orders/history">Order History</a></li>
    <li><a href="/user/logout">Logout</a></li>
</ul>

</body>
</html>
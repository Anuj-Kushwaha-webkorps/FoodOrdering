<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="/css/style.css">
	<style>
		.notification{
			color : white;
			background-color : red;
			padding : 4px;
			border-radius : 40%;
			text-decoration : none;
			text-style : none;
		}
	</style>
</head>
<body>
<h2>Welcome, Admin</h2>

<ul>
    <li><a href="/admin/restaurants">Manage Restaurants</a></li>
    <li><a href="/admin/orders">View Orders <Strong class="notification"> ${pendingOrderCount} </Strong></a></li>
    <li><a href="/admin/orders/history">Order History</a></li>
    <li><a href="/admin/logout">Logout</a></li>
</ul>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="/css/style.css">
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
		    font-size: 24px;
		}
 
		ul {
		    list-style: none;
		    padding: 0;
		    margin: 20px auto;
		    width: 300px;
		}

		li {
		    margin: 15px 0;
		}

		li a {
		    display: block;
		    text-decoration: none;
		    background-color: #007bff;
		    color: white;
		    padding: 12px;
		    border-radius: 5px;
		    font-size: 16px;
		    transition: background 0.3s ease;
		}

		li a:hover {
		    background-color: #0056b3;
		}
 
		.notification {
		    background: red;
		    color: white;
		    padding: 5px 8px;
		    border-radius: 50%;
		    font-size: 14px;
		    margin-left: 8px;
		}
 
		li a[href*="logout"] {
		    background-color: #dc3545;
		}

		li a[href*="logout"]:hover {
		    background-color: #c82333;
		}
 
		@media (max-width: 600px) {
		    ul {
		        width: 90%;
		    }

		    li a {
		        font-size: 14px;
		        padding: 10px;
		    }
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
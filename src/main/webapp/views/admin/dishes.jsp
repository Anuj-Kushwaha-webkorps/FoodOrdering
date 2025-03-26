<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Manage Dishes</title>
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
	    font-size: 24px;
	}

	a {
	    display: inline-block;
	    margin: 15px;
	    padding: 10px 15px;
	    background-color: #28a745;
	    color: white;
	    text-decoration: none;
	    border-radius: 5px;
	    font-size: 16px;
	}

	a:hover {
	    background-color: #218838;
	}

	table {
	    width: 80%;
	    margin: 20px auto;
	    border-collapse: collapse;
	    background: white;
	    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
	}

	th, td {
	    padding: 12px;
	    border: 1px solid #ddd;
	    text-align: center;
	}

	th {
	    background-color: #007bff;
	    color: white;
	}

	td a {
	    background-color: #dc3545;
	    padding: 5px 10px;
	    font-size: 14px;
	}

	td a:hover {
	    background-color: #c82333;
	}

	p {
	    font-size: 18px;
	    color: #555;
	}

	@media (max-width: 768px) {
	    table {
	        width: 100%;
	    }

	    a {
	        font-size: 14px;
	        padding: 8px 12px;
	    }
	}

</style>
<body>
<h2>Manage Dishes</h2>

<a href="/admin/restaurants/${restaurantId}/dishes/add">Add New Dish</a>

<c:if test="${not empty dishes}">
    <table border="1">
        <tr>
            <th>Name</th>
            <th>Description</th>
            <th>Price</th>
            <th>Type</th>
            <th>Size</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="dish" items="${dishes}">
            <tr>
                <td>${dish.name}</td>
                <td>${dish.description}</td>
                <td>${dish.price}</td>
                <td>${dish.dishType}</td>
                <td>${dish.dishSize}</td>
                <td>
                    <a href="/admin/restaurants/${restaurantId}/dishes/edit/${dish.dishId}">Edit</a> |
                    <a href="/admin/restaurants/${restaurantId}/dishes/delete/${dish.dishId}" onclick="return confirm('Are you sure?');">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${empty dishes}">
    <p>No dishes found.</p>
</c:if>

<a href="/admin/restaurants">Back to Restaurants</a> <strong> | </strong>
<a href="/admin/dashboard">Back to Dashboard</a>
</body>
</html>

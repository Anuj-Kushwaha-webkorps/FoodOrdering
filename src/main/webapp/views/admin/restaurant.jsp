<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Manage Restaurants</title>
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
	    border-radius: 5px;
	    display: inline-block;
	}

	table {
	    width: 90%;
	    margin: 20px auto;
	    border-collapse: collapse;
	    background: white;
	    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
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

	a {
	    display: inline-block;
	    margin: 10px;
	    padding: 8px 12px;
	    background-color: #28a745;
	    color: white;
	    text-decoration: none;
	    border-radius: 5px;
	}

	a:hover {
	    background-color: #218838;
	}

	a.delete {
	    background-color: #dc3545;
	}

	a.delete:hover {
	    background-color: #c82333;
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
<h2>Manage Restaurants</h2>

<a href="/admin/restaurants/add">Add New Restaurant</a>

<c:if test="${not empty restaurants}">
    <table border="1">
        <tr>
            <th>Name</th>
            <th>Address</th>
            <th>Contact</th>
            <th>Actions</th>
        </tr>
        <c:forEach var="restaurant" items="${restaurants}">
            <tr>
                <td><a href="/admin/restaurants/${restaurant.restaurantId}/dishes">${restaurant.name}</a></td>
                <td>${restaurant.address}</td>
                <td>${restaurant.contactNumber}</td>
                <td>
                    <a href="/admin/restaurants/edit/${restaurant.restaurantId}">Edit</a> |
                    <a href="/admin/restaurants/delete/${restaurant.restaurantId}" onclick="return confirm('Are you sure?');">Delete</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${empty restaurants}">
    <p>No restaurants found.</p>
</c:if>

<a href="/admin/dashboard">Back to Dashboard</a>
</body>
</html>

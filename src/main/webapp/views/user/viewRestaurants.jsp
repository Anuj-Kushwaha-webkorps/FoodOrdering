<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Available Restaurants</title>
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

	table {
	    width: 90%;
	    max-width: 900px;
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
	    text-align: left;
	}

	th {
	    background-color: #007bff;
	    color: white;
	}

	td a {
	    display: inline-block;
	    background-color: #28a745;
	    color: white;
	    padding: 8px 12px;
	    text-decoration: none;
	    border-radius: 5px;
	    transition: background 0.3s ease;
	}

	td a:hover {
	    background-color: #218838;
	}

	p {
	    font-size: 18px;
	    color: #555;
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
	}

</style>
<body>
<h2>Available Restaurants</h2>

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
                <td>${restaurant.name}</td>
                <td>${restaurant.address}</td>
                <td>${restaurant.contactNumber}</td>
                <td>
                    <a href="/user/restaurants/${restaurant.restaurantId}/dishes">View Menu</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${empty restaurants}">
    <p>No restaurants available at the moment.</p>
</c:if>

<a href="/user/dashboard">Back to Dashboard</a>
</body>
</html>

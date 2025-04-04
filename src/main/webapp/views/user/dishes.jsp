<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>${restaurant.name} - Menu</title>
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
	    max-width: 1000px;
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
<h2>Menu - ${restaurant.name}</h2>

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
                    <a href="/user/cart/add/${dish.dishId}">Add to Cart</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<c:if test="${empty dishes}">
    <p>No dishes available at this restaurant.</p>
</c:if>

<a href="/user/restaurants">Back to Restaurants</a>
</body>
</html>

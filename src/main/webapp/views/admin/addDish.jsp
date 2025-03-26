<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Add Dish</title>
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
	    background-color: #28a745;
	    color: white;
	    padding: 15px;
	    margin: 0;
	    font-size: 24px;
	}

	form {
	    background: white;
	    padding: 20px;
	    border-radius: 8px;
	    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
	    width: 50%;
	    margin: 20px auto;
	    text-align: left;
	}

	label {
	    font-size: 16px;
	    font-weight: bold;
	    display: block;
	    margin-top: 10px;
	}

	input, textarea, select {
	    width: 100%;
	    padding: 10px;
	    margin-top: 5px;
	    border: 1px solid #ccc;
	    border-radius: 5px;
	    font-size: 14px;
	}

	textarea {
	    resize: vertical;
	    height: 80px;
	}

	button {
	    background-color: #28a745;
	    color: white;
	    padding: 12px;
	    border: none;
	    border-radius: 5px;
	    font-size: 16px;
	    cursor: pointer;
	    width: 100%;
	    margin-top: 15px;
	}

	button:hover {
	    background-color: #218838;
	}

	a {
	    display: inline-block;
	    margin-top: 15px;
	    color: #28a745;
	    text-decoration: none;
	    font-size: 16px;
	}

	a:hover {
	    text-decoration: underline;
	}

	@media (max-width: 600px) {
	    form {
	        width: 90%;
	    }

	    button {
	        font-size: 14px;
	    }
	}

</style>
<body>
<h2>Add New Dish</h2>

<form action="/admin/restaurants/${restaurantId}/dishes/add" method="post">
    <label for="name">Dish Name:</label>
    <input type="text" id="name" name="name" required><br>

    <label for="description">Description:</label>
    <textarea id="description" name="description"></textarea><br>

    <label for="price">Price:</label>
    <input type="number" id="price" name="price" step="0.01" required><br>

    <label for="dishType">Type:</label>
    <select id="dishType" name="dishType" required>
        <option value="VEG">Veg</option>
        <option value="NON_VEG">Non-Veg</option>
    </select><br>

    <label for="dishSize">Size:</label>
    <select id="dishSize" name="dishSize" required>
        <option value="HALF">Half Plate</option>
        <option value="FULL">Full Plate</option>
    </select><br>

    <button type="submit">Add Dish</button>
</form>

<a href="/admin/restaurants/${restaurantId}/dishes">Back to Dishes</a>

<script>
	<% 
	  String error = request.getParameter("error");
	  System.out.println(error);
	  if (error != null) { 
	  %>
	      alert("<%= error %>"); 
	<% } %>
</script>
</body>
</html>
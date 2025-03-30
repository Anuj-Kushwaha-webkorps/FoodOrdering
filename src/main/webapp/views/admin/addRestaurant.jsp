<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Add Restaurant</title>
</head>
<Style>
 
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

	input, textarea {
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
	    background-color: #007bff;
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
	    background-color: #0056b3;
	}
 
	a {
	    display: inline-block;
	    margin-top: 15px;
	    color: #007bff;
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
<h2>Add New Restaurant</h2>

<form action="/admin/restaurants/add" method="post">
    <label for="name">Restaurant Name:</label>
    <input type="text" id="name" name="name" required><br>

    <label for="address">Address:</label>
    <textarea id="address" name="address" required></textarea><br>

    <label for="contact">Contact Number:</label>
    <input type="text" id="contact" name="contact" required><br>

    <button type="submit">Add Restaurant</button>
</form>

<a href="/admin/restaurants">Back to Restaurant List</a><strong> | </strong>
<a href="/admin/dashboard">Back to Dashboard</a>

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
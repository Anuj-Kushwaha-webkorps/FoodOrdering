<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin Registration</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<style>

	body {
	    font-family: Arial, sans-serif;
	    background-color: #f4f4f4;
	    margin: 0;
	    padding: 0;
	    display: flex;
	    flex-direction: column;
	    align-items: center;
	}

	h2 {
	    background-color: #007bff;
	    color: white;
	    padding: 15px;
	    width: 100%;
	    text-align: center;
	    margin: 0;
	}

	form {
	    background: white;
	    padding: 20px;
	    margin-top: 20px;
	    border-radius: 8px;
	    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	    width: 90%;
	    max-width: 400px;
	}

	label {
	    display: block;
	    margin: 10px 0 5px;
	    font-weight: bold;
	}

	input, textarea {
	    width: 100%;
	    padding: 10px;
	    border: 1px solid #ccc;
	    border-radius: 5px;
	    font-size: 16px;
	}

	.g-recaptcha {
	    margin: 15px 0;
	}

	button {
	    background-color: #28a745;
	    color: white;
	    border: none;
	    padding: 12px;
	    font-size: 16px;
	    cursor: pointer;
	    width: 100%;
	    border-radius: 5px;
	}

	button:hover {
	    background-color: #218838;
	}

	@media (max-width: 600px) {
	    form {
	        width: 95%;
	    }
	}

</style>
<body>
<h2>Admin Registration Form</h2>
  
<form action="/admin/register" method="post">
    <label for="name">Name:</label>
    <input type="text" id="name" name="name" required><br>

    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br>

    <label for="address">Address:</label>
    <textarea id="address" name="address" required></textarea><br>

    <label for="phone">Phone:</label>
    <input type="text" id="phone" name="phone" required><br>
	
	<!-- Google reCAPTCHA -->
	<div class="g-recaptcha" data-sitekey="6LfJFvcqAAAAAMu6gbNsXjNPR338LkK1_ZFlx4YE"></div>

    <button type="submit">Register</button>
</form>

<script>
	<% 
	  String error = request.getParameter("error");
	  System.out.println(error);
	  if (error != null) { 
	  %>
	      alert("<%= error %>"); 
	  <% } %>
</script>


<!-- reCAPTCHA Script -->
<script src="https://www.google.com/recaptcha/api.js" async defer></script>

</body>
</html>

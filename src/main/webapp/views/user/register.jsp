<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Registration</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<h2>User Registration Form</h2>
  
<form action="/user/register" method="post">
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

<!-- reCAPTCHA Script -->
<script src="https://www.google.com/recaptcha/api.js" async defer></script>

</body>
</html>

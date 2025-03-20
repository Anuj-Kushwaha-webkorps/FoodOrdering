<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>user Login</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<h2>User Login</h2>
<form action="/user/login" method="post">
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br>

    <button type="submit">Login</button>
</form>
</body>
</html>

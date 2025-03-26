<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>Food Ordering Website - Home</title>
    <style>
        /* Basic CSS for styling */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f8f9fa;
            text-align: center;
        }
        .header {
            background-color: #007bff;
            color: white;
            padding: 20px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
        }
        .header h1 {
            margin: 0;
            font-size: 36px;
        }
        .container {
            padding: 50px;
        }
        .links {
            display: flex;
            justify-content: center;
            gap: 20px;
            flex-wrap: wrap;
        }
        .link-box {
            background-color: #fff;
            border: 2px solid #007bff;
            border-radius: 10px;
            padding: 20px;
            width: 200px;
            text-align: center;
            text-decoration: none;
            color: #007bff;
            font-weight: bold;
            transition: background-color 0.3s, color 0.3s;
        }
        .link-box:hover {
            background-color: #007bff;
            color: white;
        }
        .footer {
            position: fixed;
            bottom: 0;
            width: 100%;
            background-color: #333;
            color: white;
            padding: 10px;
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>Welcome to Foodie Delight</h1>
        <p>Your one-stop solution for delicious meals!</p>
    </div>

    <div class="container">
        <div class="links">
            <%
                Object loggedInAdmin = session.getAttribute("loggedInAdmin");
                Object loggedInUser = session.getAttribute("loggedInUser");

                if (loggedInAdmin == null && loggedInUser == null) {
            %>
                <a href="/admin/register" class="link-box">Signup Admin</a>
                <a href="/user/register" class="link-box">Signup User</a>
                <a href="/user/login" class="link-box">Login User</a>
                <a href="/admin/login" class="link-box">Login Admin</a>
            <% } else if(loggedInAdmin == null) { %>
                <a href="/user/logout" class="link-box" style="background-color: red; color: white;">Logout</a>
            <% }else { %>
				<a href="/admin/logout" class="link-box" style="background-color: red; color: white;">Logout</a>
			<% } %>
        </div>
    </div>

    <div class="footer">
        <p>&copy; 2025 Foodie Delight. All rights reserved.</p>
    </div>
</body>
</html>

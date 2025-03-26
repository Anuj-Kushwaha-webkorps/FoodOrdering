<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Error Page</title>
</head>
<style>
	body {
	    font-family: Arial, sans-serif;
	    background-color: #f8f9fa;
	    margin: 0;
	    padding: 50px;
	    text-align: center;
	}

	h2 {
	    color: #dc3545;
	    font-size: 28px;
	}

	p {
	    font-size: 18px;
	    color: #333;
	}

	a {
	    display: inline-block;
	    margin-top: 20px;
	    padding: 10px 20px;
	    background-color: #007bff;
	    color: white;
	    text-decoration: none;
	    border-radius: 5px;
	    font-size: 16px;
	}

	a:hover {
	    background-color: #0056b3;
	}

</style>
<body>
    <h2>Oops! Something went wrong.</h2>
    <p><strong>Error:</strong> ${errorMessage}</p>
    <p><strong>URL:</strong> ${url}</p>

    <%-- Go Back Button --%>
    <c:if test="${not empty referer}">
        <a href="${referer}">Go Back</a>
    </c:if>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Edit Restaurant</title>
</head>
<style>
	
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
	    width: 50%;
	    margin: 20px auto;
	    background: white;
	    padding: 20px;
	    border-radius: 8px;
	    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
	}

	label {
	    display: block;
	    font-size: 16px;
	    margin: 10px 0 5px;
	    text-align: left;
	}

	input, textarea {
	    width: 100%;
	    padding: 10px;
	    margin-bottom: 15px;
	    border: 1px solid #ccc;
	    border-radius: 5px;
	    font-size: 14px;
	}

	button {
	    background-color: #28a745;
	    color: white;
	    padding: 10px 15px;
	    border: none;
	    border-radius: 5px;
	    font-size: 16px;
	    cursor: pointer;
	    width: 100%;
	}

	button:hover {
	    background-color: #218838;
	}

	a {
	    display: inline-block;
	    margin: 15px;
	    padding: 10px 15px;
	    background-color: #dc3545;
	    color: white;
	    text-decoration: none;
	    border-radius: 5px;
	    font-size: 16px;
	}

	a:hover {
	    background-color: #c82333;
	}

	.error{
			color : red;
			padding : 5px;
		}
	@media (max-width: 768px) {
	    form {
	        width: 90%;
	    }

	    button, a {
	        font-size: 14px;
	        padding: 8px 12px;
	    }
	}

</style>
<body>
<h2>Edit Restaurant</h2>

<form action="/admin/restaurants/edit" method="post">
    <input type="hidden" name="restaurantId" value="${restaurant.restaurantId}">

    <label for="name">Restaurant Name:</label>
    <input type="text" id="name" name="name" value="${restaurant.name}" required><br>

    <label for="address">Address:</label>
    <textarea id="address" name="address" required>${restaurant.address}</textarea><br>

    <label for="contact">Contact Number:</label>
    <input type="text" id="contact" name="contact" value="${restaurant.contactNumber}" required><br>

	<div id="errorContact" class="error"></div>

    <button id="submitBtn" type="submit">Update Restaurant</button>
</form>

<a href="/admin/restaurants">Back to Restaurant List</a><strong> | </strong>
<a href="/admin/dashboard">Back to Dashboard</a>

<script>
	document.getElementById('submitBtn').addEventListener('click',
		    function(e) {
				e.preventDefault();
				
		        const formData = {
					restaurantId: document.querySelector('input[name="restaurantId"]').value,
		            name: document.querySelector('input[name="name"]').value,
		            address: document.querySelector('textarea[name="address"]').value,
		            contact: document.querySelector('input[name="contact"]').value	            
		        };
									
		        fetch('/admin/restaurants/edit', {
		            method: 'POST',
		            headers: {
		                'Content-Type': 'application/json' 
		            },
		            body: JSON.stringify(formData)
		        })
		        .then(response => response.json())  
		        .then(data => {
					if(data.redirectUrl != null) {
		            	 window.location.href = data.redirectUrl;
		            }else{
						document.getElementById("errorContact").innerText = data.errorContact;
					}
		        })
		        .catch(error => {
		            console.error('Error:', error);
		        });
		    }
	);
</script>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Edit Dish</title>
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

	input, textarea, select {
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
<h2>Edit Dish</h2>

<form action="/admin/restaurants/${restaurantId}/dishes/edit" method="post">
    <input type="hidden" name="dishId" value="${dish.dishId}">

    <label for="name">Dish Name:</label>
    <input type="text" id="name" name="name" value="${dish.name}" required><br>

    <label for="description">Description:</label>
    <textarea id="description" name="description">${dish.description}</textarea><br>

    <label for="price">Price:</label>
    <input type="number" id="price" name="price" value="${dish.price}" step="0.01" required><br>

	<div id="errorPrice" class="error"></div>
	
    <label for="dishType">Type:</label>
    <select id="dishType" name="dishType" required>
        <option value="VEG" ${dish.dishType == 'VEG' ? 'selected' : ''}>Veg</option>
        <option value="NON_VEG" ${dish.dishType == 'NON_VEG' ? 'selected' : ''}>Non-Veg</option>
    </select><br>

    <label for="dishSize">Size:</label>
    <select id="dishSize" name="dishSize" required>
        <option value="HALF" ${dish.dishSize == 'HALF' ? 'selected' : ''}>Half Plate</option>
        <option value="FULL" ${dish.dishSize == 'FULL' ? 'selected' : ''}>Full Plate</option>
    </select><br>

    <button id="submitBtn" type="submit">Update Dish</button>
</form>

<a href="/admin/restaurants/${restaurantId}/dishes">Back to Dishes</a>

<script>
	document.getElementById('submitBtn').addEventListener('click',
			    function(e) {
					e.preventDefault();
					
			        const formData = {
						dishId: document.querySelector('input[name="dishId"]').value,
			            name: document.querySelector('input[name="name"]').value,
			            description: document.querySelector('textarea[name="description"]').value,
			            price: document.querySelector('input[name="price"]').value,
			            dishType : document.querySelector('#dishType').value,
						dishSize : document.querySelector('#dishSize').value,
			        };
					
			        fetch('/admin/restaurants/<%= request.getAttribute("restaurantId") %>/dishes/edit', {
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
							document.getElementById("errorPrice").innerText = data.errorPrice;
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
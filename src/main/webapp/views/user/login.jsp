<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>user Login</title>
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
	    max-width: 350px;
	}

	label {
	    display: block;
	    margin: 10px 0 5px;
	    font-weight: bold;
	}

	input {
	    width: 100%;
	    padding: 10px;
	    border: 1px solid #ccc;
	    border-radius: 5px;
	    font-size: 16px;
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
	a {
	    display: block;
	    text-decoration: none;
	    background-color: #007bff;
	    color: white;
	    padding: 12px;
	    border-radius: 5px;
	    font-size: 16px;
	    transition: background 0.3s ease;
	}
	
	.error{
		color : red;
		padding : 5px;
	}

	@media (max-width: 600px) {
	    form {
	        width: 95%;
	    }
	}

</style>
<body>
<h2>User Login</h2>
<form action="/user/login" method="post">
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br>

	<div id="errorMsg" class="error"></div>

    <button id="submitBtn" type="submit">Login</button>
</form>

<a href="/">Home</a>


<script>
	document.getElementById('submitBtn').addEventListener('click',
			    function(e) {
					e.preventDefault();
					
			        const formData = {
			            email: document.querySelector('input[name="email"]').value,
			            password: document.querySelector('input[name="password"]').value		            
			        };
					
			        fetch('/user/login', {
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
							document.getElementById('errorMsg').innerText = data.errorMsg;
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

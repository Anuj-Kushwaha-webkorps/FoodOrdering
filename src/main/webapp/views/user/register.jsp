<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Registration</title>
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
	
	a {
			    display: block;
			    text-decoration: none;
			    background-color: #007bff;
			    color: white;
			    padding: 12px;
			    border-radius: 5px;
			    font-size: 16px;
			}
 
	@media (max-width: 600px) {
	    form {
	        width: 95%;
	    }
	}

</style>
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

    <button id="submitBtn" type="submit">Register</button>
</form>
<a href="/">Home</a>


<script>
	
	document.getElementById('submitBtn').addEventListener('click',
		    function(e) {
				e.preventDefault();
		        const formData = {
		            name: document.querySelector('input[name="name"]').value,
		            email: document.querySelector('input[name="email"]').value,
		            password: document.querySelector('input[name="password"]').value,
		            address : document.querySelector('textarea[name="address"]').value,
					phone : document.querySelector('input[name="phone"]').value,
					recaptcha: grecaptcha.getResponse()
		        };
		        fetch('/user/register', {
		            method: 'POST',
		            headers: {
		                'Content-Type': 'application/json' 
		            },
		            body: JSON.stringify(formData)
		        })
		        .then(response => response.json())  
		        .then(data => {
		            if (data.redirectUrl) {
		            	console.log("in redirect");
		            	 window.location.href = data.redirectUrl;
		            }
		        })
		        .catch(error => {
		            console.error('Error:', error);
		        });
		    }
		    );
			
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

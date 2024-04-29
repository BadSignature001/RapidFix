<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style>
body {
	font-family: 'Montserrat', sans-serif;
	background-color: #f0f8ff;
	margin: 0;
	padding: 0;
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
}

a {
	display: block;
	text-align: center;
	text-decoration: none;
	color: #388e3c;
	font-size: 16px;
	margin-top: 15px;
	transition: color 0.2s ease-in-out;
}

a:hover {
	color: #2e7d32;
}

.container {
	background-color: white;
	padding: 40px;
	border-radius: 10px;
	box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
	width: 400px;
	max-width: 100%;
}

h2 {
	margin-bottom: 20px;
	color: #333;
	font-size: 24px;
	font-weight: bold;
}

form {
	display: flex;
	flex-direction: column;
	align-items: center;
	gap: 15px;
}

input[type="text"], input[type="password"] {
	width: 100%;
	padding: 15px;
	border: 1px solid #ddd;
	border-radius: 5px;
	box-sizing: border-box;
	background-color: #fff;
	color: #333;
	font-size: 16px;
	outline: none;
}

input[type="text"]:focus, input[type="password"]:focus {
	border: 1px solid #388e3c;
}

button[type="submit"] {
	width: 100%;
	padding: 15px;
	border: none;
	border-radius: 5px;
	background-color: #388e3c;
	color: white;
	font-size: 16px;
	font-weight: bold;
	cursor: pointer;
	outline: none;
	transition: background-color 0.2s ease-in-out;
}

button[type="submit"]:hover {
	background-color: #2e7d32;
}

#recaptcha-container {
    margin-top: 15px;
    display: flex;
    justify-content: center;
    align-items: center;
}

.g-recaptcha {
    transform: scale(0.85); /* Adjust the scale as needed */
}

</style>
</head>
<body>
	<div class="container">
		<h2>RapidFix</h2>
		   <form action="/endpoint/riders/signup" method="post">
        <input type="text" name="username" placeholder="Enter your Name" required>
        <input id="mobile" type="text" name="mobileNo" placeholder="Mobile Number" required>
        <input type="password" name="password" placeholder="Create a Password" required>
        <input type="password" name="confirmPassword" placeholder="Confirm Password" required>
        
         <div id="recaptcha-container"></div>
        
        <input type="checkbox" name="checkboxName" value="Send OTP" onClick="phoneAuth()">Send OTP 
        <input type="text" name="confirmOtp" placeholder="Confirm OTP" required>

        <button type="submit">Register as a Rider</button>
    </form>
    
  <div class="error-message">
        <% if (request.getAttribute("errorMessage") != null) { %>
            <%= request.getAttribute("errorMessage") %>
        <% } %>
    </div>
    <div class="success-message">
        <% if (request.getAttribute("successMessage") != null) { %>
            <%= request.getAttribute("successMessage") %>
        <% } %>
    </div>
    
    </div> 
		
 		<script src="https://www.gstatic.com/firebasejs/9.12.1/firebase-app-compat.js"></script>
        <script src="https://www.gstatic.com/firebasejs/9.12.1/firebase-auth-compat.js"></script>

		<script>
		
		const firebaseConfig = {
				  apiKey: "AIzaSyA5oaLE7e_FLfSuLirAmI35PodaPvs9Fkc",
				  authDomain: "rapidfix-44ff2.firebaseapp.com",
				  projectId: "rapidfix-44ff2",
				  storageBucket: "rapidfix-44ff2.appspot.com",
				  messagingSenderId: "238897333660",
				  appId: "1:238897333660:web:718ceb6933ad25d2e8b459",
				  measurementId: "G-QH469R3T3E"
				};
		 firebase.initializeApp(firebaseConfig);

         render();

         function render() {
             window.recaptchaVerifier = new firebase.auth.RecaptchaVerifier('recaptcha-container');
             recaptchaVerifier.render();
         }

         let coderesult1;
         
         function phoneAuth() {
        	 
        	 
        	 
             var number = document.getElementById('mobile').value;
             firebase.auth().signInWithPhoneNumber(number, window.recaptchaVerifier).then(function (confirmationResult) {
             //   window.confirmationResult = confirmationResult;
               //  coderesult = confirmationResult;           
              //   document.getElementById('coderesult').value = coderesult;
              
              console.log(confirmationResult) ;
             
             if (confirmationResult !== null) {
            coderesult1 = confirmationResult;
            sendCodeResultToSpringBoot(coderesult1);
        } else {
            console.error("Confirmation result is null");
        }

            }).catch(function (error) {

                 alert(error.message);
            });
         }

         function sendCodeResultToSpringBoot(coderesult1) {
             // Make an Ajax request to Spring Boot endpoint
             var xhr = new XMLHttpRequest();
             xhr.open("POST", "/signup", true);
             xhr.setRequestHeader('Content-Type', 'application/json');
             xhr.send(JSON.stringify(coderesult1));
         }
			
		</script> 
		



	
</body>
</html>
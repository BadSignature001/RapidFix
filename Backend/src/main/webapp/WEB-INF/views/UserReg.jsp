<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>RapidFix! - User Registration</title>
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

    input[type="text"],
    input[type="password"] {
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

    input[type="text"]:focus,
    input[type="password"]:focus {
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
  </style>
</head>
<body>
  <div class="container">
    <h2>RapidFix</h2>
    <form action="/endpoint/users/signup" method="post">
      <input type="text" name="username" placeholder="Enter your Name" required>
      <input type="text" name="mobileNo" placeholder="Mobile Number" required>
      <input type="password" name="password" placeholder="Create a Password" required>
      <input type="password" name="confirmPassword" placeholder="Confirm Password" required>
      <button type="submit">Register as a User</button>
    </form>
    <a href="/endpoint/users/rapidfixcommunity">Join to Community</a>

  </div>
</body>
</html>

<!-- sahilbhukal -->

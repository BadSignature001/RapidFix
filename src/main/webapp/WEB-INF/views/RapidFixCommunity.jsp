<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
  pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="ISO-8859-1">
  <title>RapidFix Community</title>
  <style>
    /* Global Styles */
    body {
      font-family: 'Poppins', sans-serif; /* Modern, friendly font */
      background-color: #f5f5f5;
      margin: 0;
      padding: 0;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
    }

    /* Container Styles (optional, for a more defined area) */
    .container {
      background-color: white;
      padding: 40px;
      border-radius: 8px;
      box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1); /* Softer and more modern shadow */
      text-align: center;
      width: 400px; /* Adjust width as needed */
      max-width: 100%; /* Responsive sizing */
    }

    h2 {
      margin-bottom: 20px;
      color: #333; /* Darker title color */
      font-size: 28px; /* Larger, more prominent title */
      font-weight: bold;
    }

    /* Form Styles */
    form {
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 15px;
    }

    /* Button Styles */
    button[type="submit"] {
      width: 100%;
      padding: 18px; /* Consistent padding for better spacing */
      border: none;
      border-radius: 5px;
      cursor: pointer;
      outline: none;
      transition: background-color 0.2s ease-in-out;
    }

    button[type="submit"]:enabled {  /* Styles for enabled button */
      background-color: #388e3c; /* Green primary color */
      color: white;
      font-size: 16px;
      font-weight: bold;
    }

    button[type="submit"]:disabled {  /* Styles for disabled button */
      background-color: #ddd; /* Light gray for disabled button */
      color: #999; /* Grayed-out text for disabled button */
      cursor: default; /* Remove pointer cursor on disabled button */
    }

    button[type="submit"]:hover:enabled { /* Hover effect for enabled button */
      background-color: #2e7d32; /* Darker green on hover */
    }
  </style>
</head>
<body>
  <div class="container">
    <h2>Join the RapidFix Community</h2>
    <form action="/endpoint/riders/workers" method="get">
      <button type="submit">Register as a Rider</button>
    </form>
    <button type="submit" disabled>Register as a Shop Owner (Coming Soon)</button>
  </div>
  </body>
</html>
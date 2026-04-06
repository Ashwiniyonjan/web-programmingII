<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Registration</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="card">
        <h1>User Registration</h1>
        
        <% if (request.getAttribute("errorMessage") != null) { %>
            <p style="color: red;">${errorMessage}</p>
        <% } %>

        <form action="${pageContext.request.contextPath}/signup" method="post">
            <label for="firstName">First Name</label>
            <input type="text" id="firstName" name="firstName" required>

            <label for="lastName">Last Name</label>
            <input type="text" id="lastName" name="lastName" required>

            <label for="email">Email Address</label>
            <input type="email" id="email" name="email" required>

            <label for="password">Password</label>
            <input type="password" id="password" name="password" required minlength="6" placeholder="At least 6 characters" style="padding: 8px; border: 1px solid #ccc; border-radius: 4px; width: 100%; box-sizing: border-box; margin-bottom: 5px;">
            <small style="color: #666; font-size: 0.8em; margin-bottom: 15px; display: block;">* Must be at least 6 characters</small>

            <label for="phone">Phone Number (optional)</label>
            <input type="tel" id="phone" name="phone">

            <button type="submit">Create My Account</button>
        </form>
        
        <p style="text-align: center; margin-top: 15px;">
            Already have an account? <a href="${pageContext.request.contextPath}/login">Sign In</a>
        </p>
    </div>
</body>
</html>
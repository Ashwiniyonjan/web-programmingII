<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign In</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="card">
        <h1>Sign In</h1>

        <% if (request.getAttribute("errorMessage") != null) { %>
            <p style="color: red;">${errorMessage}</p>
        <% } %>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <label for="email">Email Address</label>
            <input type="email" id="email" name="email" required>

            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>

            <button type="submit">Sign In</button>
        </form>
        
        <p style="text-align: center; margin-top: 15px;">
            Don't have an account? <a href="${pageContext.request.contextPath}/signup">Create one free</a>
        </p>
    </div>
</body>
</html>

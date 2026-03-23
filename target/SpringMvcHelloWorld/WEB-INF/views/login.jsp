<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="style.css"/>
</head>
<body class="bg-light d-flex justify-content-center align-items-center" style="min-height: 100vh;">
    <div class="card shadow-sm p-4" style="max-width: 400px; width: 100%;">
        <h2 class="text-success mb-4 text-center">Login</h2>

        <p class="text-danger">${error}</p>
        <p class="text-success">${message}</p>

        <form action="${pageContext.request.contextPath}/user/login" method="post">
            <div class="mb-3">
                <input type="email" name="email" class="form-control" placeholder="Email" required>
            </div>
            <div class="mb-3">
                <input type="password" name="password" class="form-control" placeholder="Password" required>
            </div>
            <button type="submit" class="btn btn-success w-100">Login</button>
        </form>

        <div class="mt-3 text-center">
            <a href="${pageContext.request.contextPath}/user/register">Don't have an account? Register</a>
        </div>
        <div class="mt-2 text-center">
            <a href="${pageContext.request.contextPath}/home">Back to Home</a>
        </div>
    </div>
</body>
</html>
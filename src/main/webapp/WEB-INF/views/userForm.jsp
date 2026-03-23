<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Registration</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="style.css"/>
</head>
<body class="bg-light d-flex justify-content-center align-items-center" style="min-height: 100vh;">
    <div class="card shadow-sm p-4" style="max-width: 500px; width: 100%;">
        <h2 class="text-success mb-4 text-center">Register</h2>

        <p class="text-danger">${error}</p>

        <form action="${pageContext.request.contextPath}/user/register" method="post">
            <div class="mb-3">
                <input type="text" name="name" class="form-control" placeholder="Full Name" required>
            </div>
            <div class="mb-3">
                <input type="email" name="email" class="form-control" placeholder="Email" required>
            </div>
            <div class="mb-3">
                <input type="password" name="password" class="form-control" placeholder="Password" required>
            </div>
            <div class="mb-3">
                <input type="tel" name="phone" class="form-control" placeholder="Phone Number">
            </div>
            <div class="mb-3">
                <input type="text" name="address" class="form-control" placeholder="Address">
            </div>
            <div class="mb-3">
                <select name="gender" class="form-select">
                    <option value="" selected disabled>Gender</option>
                    <option value="Male">Male</option>
                    <option value="Female">Female</option>
                    <option value="Other">Other</option>
                </select>
            </div>
            <div class="form-check mb-3">
                <input type="checkbox" name="agree" class="form-check-input" id="agree">
                <label class="form-check-label" for="agree">I agree to the Terms and Conditions.</label>
            </div>

            <button type="submit" class="btn btn-success w-100">Register</button>
        </form>

        <div class="mt-3 text-center">
            <a href="${pageContext.request.contextPath}/user/login">Already have an account? Login</a>
        </div>
        <div class="mt-2 text-center">
            <a href="${pageContext.request.contextPath}/home">Back to Home</a>
        </div>
    </div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Successful</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="style.css"/>
</head>
<body class="bg-light d-flex justify-content-center align-items-center" style="min-height: 100vh;">
    <div class="card shadow-sm p-4 text-center" style="max-width: 500px; width: 100%;">
        <h2 class="text-success mb-4">${message}</h2>

        <p><b>Name:</b> ${user.name}</p>
        <p><b>Email:</b> ${user.email}</p>
        <c:if test="${not empty user.phone}">
            <p><b>Phone:</b> ${user.phone}</p>
        </c:if>
        <c:if test="${not empty user.address}">
            <p><b>Address:</b> ${user.address}</p>
        </c:if>
        <c:if test="${not empty user.gender}">
            <p><b>Gender:</b> ${user.gender}</p>
        </c:if>

        <a href="${pageContext.request.contextPath}/home" class="btn btn-primary mt-3">Back to Home</a>
    </div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Product Summary</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; text-align: center; }
        .summary-card { background-color: white; padding: 30px; border-radius: 8px; max-width: 400px; margin: auto; box-shadow: 0px 0px 10px rgba(0,0,0,0.1); }
        .summary-card img { max-width: 100%; height: auto; border-radius: 5px; margin-top: 15px; }
        .summary-card h3 { color: #333; }
        .summary-card p { font-size: 16px; color: #555; }
        .btn-home { display: inline-block; margin-top: 20px; padding: 10px 20px; background-color: #007bff; color: white; text-decoration: none; border-radius: 4px; }
        .btn-home:hover { background-color: #0056b3; }
    </style>
</head>
<body>

<div class="summary-card">
    <h2>Product Successfully Added!</h2>
    <h3>${product.name}</h3>
    <p>${product.description}</p>
    <p><strong>Price:</strong> $${product.price}</p>
    <p><strong>Offer:</strong> ${product.offer}</p>

    <!-- Display the uploaded image -->
    <img src="${pageContext.request.contextPath}${product.imagePath}" alt="${product.name}" />

    <br/>
    <a href="${pageContext.request.contextPath}/home" class="btn-home">Go to Home</a>
</div>

</body>
</html>

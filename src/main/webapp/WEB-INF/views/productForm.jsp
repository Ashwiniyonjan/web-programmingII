<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Product</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px; }
        .form-container { background-color: white; padding: 30px; border-radius: 8px; max-width: 500px; margin: auto; box-shadow: 0px 0px 10px rgba(0,0,0,0.1); }
        .form-container h2 { text-align: center; }
        .form-group { margin-bottom: 15px; }
        .form-group label { display: block; margin-bottom: 5px; font-weight: bold; }
        .form-group input, .form-group textarea { width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        .btn-submit { width: 100%; padding: 10px; background-color: #28a745; color: white; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; }
        .btn-submit:hover { background-color: #218838; }
        .error { color: red; text-align: center; }
    </style>
</head>
<body>

<div class="form-container">
    <h2>Add New Product</h2>
    <% if (request.getParameter("error") != null) { %>
        <p class="error">There was an error uploading the image. Please try again.</p>
    <% } %>
    <form action="${pageContext.request.contextPath}/product/add" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="name">Product Name:</label>
            <input type="text" id="name" name="name" required>
        </div>
        <div class="form-group">
            <label for="description">Description:</label>
            <textarea id="description" name="description" rows="4" required></textarea>
        </div>
        <div class="form-group">
            <label for="price">Price ($):</label>
            <input type="number" id="price" name="price" step="0.01" required>
        </div>
        <div class="form-group">
            <label for="offer">Offer/Discount:</label>
            <input type="text" id="offer" name="offer">
        </div>
        <div class="form-group">
            <label for="imageFile">Product Image:</label>
            <input type="file" id="imageFile" name="imageFile" accept="image/*" required>
        </div>
        <button type="submit" class="btn-submit">Add Product</button>
    </form>
</div>

</body>
</html>

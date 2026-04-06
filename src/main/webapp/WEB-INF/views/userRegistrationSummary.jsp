<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome to ASHH Clothing — Registration Successful</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@400;600&family=Inter:wght@300;400;500;600&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Inter', sans-serif;
            background: #f8f4f0;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

        .announcement-bar {
            background: #1a1a1a;
            color: #fff;
            text-align: center;
            padding: 8px;
            font-size: 0.75rem;
            letter-spacing: 1.5px;
        }

        .navbar {
            background: #fff;
            border-bottom: 1px solid #e8e0d8;
            padding: 16px 24px;
        }
        .navbar-brand {
            font-family: 'Playfair Display', serif;
            font-size: 1.6rem;
            font-weight: 600;
            color: #1a1a1a !important;
            text-decoration: none;
        }

        .page-wrapper {
            flex: 1;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 60px 16px;
        }

        .summary-card {
            background: #fff;
            border-radius: 16px;
            box-shadow: 0 8px 40px rgba(0,0,0,0.08);
            width: 100%;
            max-width: 500px;
            overflow: hidden;
        }

        .success-band {
            background: linear-gradient(135deg, #1a1a1a 0%, #3a2a2a 100%);
            color: #fff;
            padding: 36px 40px 28px;
            text-align: center;
        }
        .success-band .check-circle {
            width: 64px;
            height: 64px;
            background: rgba(255,255,255,0.12);
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            margin: 0 auto 16px;
            font-size: 1.6rem;
        }
        .success-band h1 {
            font-family: 'Playfair Display', serif;
            font-size: 1.6rem;
            margin-bottom: 6px;
        }
        .success-band p {
            font-size: 0.85rem;
            color: rgba(255,255,255,0.7);
        }

        .summary-body { padding: 36px 40px 40px; }

        .info-table {
            width: 100%;
            border-collapse: separate;
            border-spacing: 0;
        }
        .info-table tr:not(:last-child) td {
            border-bottom: 1px solid #f0ebe5;
        }
        .info-table td {
            padding: 12px 0;
            font-size: 0.88rem;
        }
        .info-table td:first-child {
            color: #888;
            font-weight: 500;
            text-transform: uppercase;
            letter-spacing: 0.8px;
            font-size: 0.75rem;
            width: 40%;
        }
        .info-table td:last-child { color: #1a1a1a; font-weight: 500; }

        .id-badge {
            display: inline-block;
            background: #f0ebe5;
            color: #1a1a1a;
            border-radius: 6px;
            padding: 2px 10px;
            font-weight: 600;
            font-size: 0.85rem;
        }

        .btn-home {
            display: block;
            width: 100%;
            background: #1a1a1a;
            color: #fff;
            border: none;
            border-radius: 8px;
            padding: 13px;
            font-size: 0.9rem;
            font-weight: 500;
            letter-spacing: 0.8px;
            text-transform: uppercase;
            text-align: center;
            text-decoration: none;
            margin-top: 28px;
            transition: background 0.25s, transform 0.15s;
        }
        .btn-home:hover { background: #333; color: #fff; transform: translateY(-1px); }
.btn-secondary-link {
            display: block;
            text-align: center;
            color: #1a1a1a;
            font-size: 0.85rem;
            font-weight: 500;
            margin-top: 14px;
            text-decoration: none;
        }
        .btn-secondary-link:hover { text-decoration: underline; color: #1a1a1a; }

        footer {
            text-align: center;
            padding: 20px;
            font-size: 0.75rem;
            color: #aaa;
            border-top: 1px solid #eee;
            background: #fff;
        }
    </style>
</head>
<body>

    <div class="announcement-bar">FREE STANDARD SHIPPING ON ORDERS OVER £100</div>

    <nav class="navbar">
        <div class="container d-flex align-items-center justify-content-between">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">ASHH Clothing</a>
            <a href="${pageContext.request.contextPath}/" class="btn btn-dark btn-sm">Shop Now</a>
        </div>
    </nav>

    <div class="page-wrapper">
        <div class="summary-card">

            <!-- Success header -->
            <div class="success-band">
                <div class="check-circle">
                    <i class="fa-solid fa-check"></i>
                </div>
                <h1>You're In!</h1>
                <p>Your account has been created successfully.</p>
            </div>

            <!-- Details body -->
            <div class="summary-body">
                <table class="info-table">
                    <tr>
                        <td>Account ID</td>
                        <td><span class="id-badge">#${user.id}</span></td>
                    </tr>
                    <tr>
                        <td>Full Name</td>
                        <td>${user.firstName} ${user.lastName}</td>
                    </tr>
                    <tr>
                        <td>Email</td>
                        <td>${user.email}</td>
                    </tr>
                    <% if (request.getAttribute("user") != null &&
                           ((com.example.model.User)request.getAttribute("user")).getPhone() != null &&
                           !((com.example.model.User)request.getAttribute("user")).getPhone().isBlank()) { %>
                    <tr>
                        <td>Phone</td>
                        <td>${user.phone}</td>
                    </tr>
                    <% } %>
                </table>

                <a href="${pageContext.request.contextPath}/" class="btn-home">
                    <i class="fa-solid fa-bag-shopping me-2"></i>Start Shopping
                </a>
                <a href="${pageContext.request.contextPath}/signup" class="btn-secondary-link">
                    Register another account
                </a>
            </div>
        </div>
    </div>

    <footer>&copy; 2024 ASHH Clothing.com — All rights reserved.</footer>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

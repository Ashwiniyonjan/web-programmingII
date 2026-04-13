<!doctype html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>ASHH Clothing</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="icon" href="favicon.png" />
    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <link
      href="https://fonts.googleapis.com/css2?family=Playfair+Display:wght@700;900&display=swap"
      rel="stylesheet"
    />
    <link
      rel="stylesheet"
      href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css"
    />
    <link rel="stylesheet" href="style.css" />
    <style>
      body {
        font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
        margin: 0;
        background-color: #f0f2f5;
        color: #333;
      }
      .ashh-title span {
        color: #2e7d32;
      }
    </style>
  </head>
  <body>
    <!-- Sticky Header & Navbar -->
    <div class="sticky-top bg-white">
      <header class="text-center bg-white pt-4 pb-3 border-bottom">
        <h1 class="ashh-title">ASHH <span>Clothing</span></h1>
        <p class="ashh-subtitle">Your Everyday Fashion Destination</p>
      </header>

      <nav class="navbar navbar-expand-lg bg-white py-3 shadow-sm">
        <div class="container-fluid">
          <!-- Logo (small screens) -->
          <a class="navbar-brand d-lg-none" href="#">ASHH</a>

          <!-- Hamburger -->
          <button
            class="navbar-toggler"
            type="button"
            data-bs-toggle="collapse"
            data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span class="navbar-toggler-icon"></span>
          </button>

          <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <!-- Search (left) -->
            <div class="d-flex flex-grow-1" style="flex-basis: 0">
              <form class="d-flex w-100 mt-3 mb-2 mt-lg-0 mb-lg-0 nav-left">
                <div class="input-group search-container">
                  <input
                    class="form-control text-muted border-0 bg-light"
                    type="search"
                    placeholder="Search"
                    aria-label="Search"
                    style="border-radius: 20px 0 0 20px"
                  />
                  <button
                    class="btn text-white"
                    type="submit"
                    style="
                      background-color: #6b7091;
                      border-radius: 0 20px 20px 0;
                    "
                  >
                    <svg
                      xmlns="http://www.w3.org/2000/svg"
                      width="18"
                      height="18"
                      fill="currentColor"
                      class="bi bi-search"
                      viewBox="0 0 16 16"
                    >
                      <path
                        d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"
                      />
                    </svg>
                  </button>
                </div>
              </form>
            </div>

            <!-- Center links -->
            <ul
              class="navbar-nav mx-auto mb-2 mb-xl-0 nav-center gap-3 justify-content-center"
            >
              <li class="nav-item dropdown">
                <a
                  class="nav-link dropdown-toggle"
                  href="#"
                  role="button"
                  data-bs-toggle="dropdown"
                  aria-expanded="false"
                  >Bottoms</a
                >
                <ul class="dropdown-menu">
                  <li><a class="dropdown-item" href="jeans.html">Jeans</a></li>
                  <li>
                    <a class="dropdown-item" href="skirts.html">Skirts</a>
                  </li>
                </ul>
              </li>
              <li class="nav-item dropdown">
                <a
                  class="nav-link dropdown-toggle"
                  href="#"
                  role="button"
                  data-bs-toggle="dropdown"
                  aria-expanded="false"
                  >Tops</a
                >
                <ul class="dropdown-menu">
                  <li>
                    <a class="dropdown-item" href="tshirts.html">T-Shirts</a>
                  </li>
                  <li>
                    <a class="dropdown-item" href="shirts.html">Shirts</a>
                  </li>
                </ul>
              </li>
              <li class="nav-item dropdown">
                <a
                  class="nav-link dropdown-toggle"
                  href="#"
                  role="button"
                  data-bs-toggle="dropdown"
                  aria-expanded="false"
                  >Casual</a
                >
                <ul class="dropdown-menu">
                  <li>
                    <a class="dropdown-item" href="dailywear.html"
                      >Daily Wear</a
                    >
                  </li>
                  <li>
                    <a class="dropdown-item" href="streetstyle.html"
                      >Street Style</a
                    >
                  </li>
                </ul>
              </li>
              <li class="nav-item dropdown">
                <a
                  class="nav-link dropdown-toggle"
                  href="#"
                  role="button"
                  data-bs-toggle="dropdown"
                  aria-expanded="false"
                  >Party</a
                >
                <ul class="dropdown-menu">
                  <li>
                    <a class="dropdown-item" href="dresses.html">Dresses</a>
                  </li>
                  <li><a class="dropdown-item" href="gowns.html">Gowns</a></li>
                </ul>
              </li>
            </ul>

            <!-- Right: Register/Login & Cart -->
            <div
              class="nav-right d-flex gap-3 align-items-center justify-content-center justify-content-lg-end mt-2 mt-lg-0 flex-grow-1"
              style="flex-basis: 0"
            >
              <!-- REGISTER -->
              <a
                href="${pageContext.request.contextPath}/signup"
                class="btn btn-outline-primary"
              >
                <i class="fa-solid fa-user me-1"></i>
                Register / Login
              </a>

              <!-- CART -->
              <a href="#" class="btn btn-primary position-relative">
                <i class="fa-solid fa-cart-shopping"></i>
                <span
                  class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger"
                  style="font-size: 0.6em"
                  >0
                </span>
              </a>
            </div>
          </div>
        </div>
      </nav>
    </div>

    <!-- Hero Video Section -->
    <section id="video" class="position-relative bg-dark">
      <div class="container-fluid p-0">
        <video autoplay muted loop class="custom-video">
          <source src="images/video.mp4" type="video/mp4" />
          Your browser does not support the video tag.
        </video>
        <!-- Hero Overlay -->
        <div
          class="position-absolute top-0 start-0 w-100 h-100 d-flex align-items-center"
          style="
            background: linear-gradient(
              to right,
              rgba(0, 0, 0, 0.4) 0%,
              rgba(0, 0, 0, 0.1) 60%,
              transparent 100%
            );
          "
        >
          <div class="text-white hero-content text-center">
            <h1
              class="hero-heading fw-bold mb-3"
              style="
                letter-spacing: 2px;
                text-shadow: 0 3px 15px rgba(0, 0, 0, 0.5);
              "
            >
              Wear Your Confidence
            </h1>
            <p
              class="hero-tagline mb-0"
              style="
                opacity: 0.92;
                line-height: 1.7;
                text-shadow: 0 1px 6px rgba(0, 0, 0, 0.45);
              "
            >
              Own the room. Wear it bold, wear it soft.
            </p>
          </div>
        </div>
      </div>
    </section>

    <!-- Summer Trends Section -->
    <section
      id="summerSection"
      class="position-relative w-100"
      style="overflow: hidden"
    >
      <!-- Three images side by side, horizontally scrollable -->
      <div class="d-flex flex-nowrap overflow-x-auto summer-img-row">
        <img
          src="images/summer-product1.png"
          class="summer-img flex-shrink-0"
          alt="Summer 1"
        />
        <img
          src="images/summer-product2.png"
          class="summer-img flex-shrink-0"
          alt="Summer 2"
        />
        <img
          src="images/summer-product3.png"
          class="summer-img flex-shrink-0"
          alt="Summer 3"
        />
      </div>

      <!-- Overlay text and button -->
      <div
        class="position-absolute top-0 start-0 w-100 h-100 d-flex flex-column justify-content-center align-items-center text-center"
        style="background: rgba(0, 0, 0, 0.3); pointer-events: none"
      >
        <h2
          class="text-white fw-bold mb-3"
          style="
            pointer-events: auto;
            font-size: clamp(1.2rem, 3vw, 2.2rem);
            letter-spacing: 2px;
          "
        >
          Upcoming Summer Trends
        </h2>
        <a
          href="summer.html"
          class="btn btn-light px-4 py-2 fw-semibold"
          style="border-radius: 30px; pointer-events: auto"
        >
          Explore &nbsp;&rarr;
        </a>
      </div>
    </section>

    <!-- Featured Products Section -->
    <section id="products" class="container-fluid px-5 py-5">
      <div class="row mb-4">
        <div class="col-12 text-center">
          <h2 class="section-heading">Featured Products</h2>
          <p class="section-sub">Handpicked favorites just for you</p>
        </div>
      </div>

      <!-- Scroll wrapper: left/right arrows on mobile -->
      <div class="scroll-wrapper">
        <button
          class="scroll-arrow left"
          onclick="
            document
              .getElementById('productScroll')
              .scrollBy({ left: -220, behavior: 'smooth' })
          "
        >
          &laquo;
        </button>
        <button
          class="scroll-arrow right"
          onclick="
            document
              .getElementById('productScroll')
              .scrollBy({ left: 220, behavior: 'smooth' })
          "
        >
          &raquo;
        </button>

        <!-- Horizontal Scroll Container -->
        <div
          id="productScroll"
          class="row flex-nowrap overflow-auto pb-4 g-4 justify-content-lg-center"
          style="scroll-behavior: smooth"
        >
          <!-- Product 1 -->
          <div class="col-10 col-md-4 col-lg-2 flex-shrink-0">
            <div class="card h-100 border-0 shadow-sm product-card">
              <img
                src="images/product1.png"
                class="card-img-top"
                alt="Product 1"
              />
              <div class="card-body text-center">
                <h5 class="card-title">Off Shoulder Tee</h5>
                <p class="card-text text-muted">NPR 2500</p>
                <a
                  href="product1.html"
                  class="btn btn-primary btn-sm stretched-link"
                  >View Details</a
                >
              </div>
            </div>
          </div>
          <!-- Product 2 -->
          <div class="col-10 col-md-4 col-lg-2 flex-shrink-0">
            <div class="card h-100 border-0 shadow-sm product-card">
              <img
                src="images/product2.png"
                class="card-img-top"
                alt="Product 2"
              />
              <div class="card-body text-center">
                <h5 class="card-title">Crop Tee</h5>
                <p class="card-text text-muted">NPR 1800</p>
                <a
                  href="product2.html"
                  class="btn btn-primary btn-sm stretched-link"
                  >View Details</a
                >
              </div>
            </div>
          </div>
          <!-- Product 3 -->
          <div class="col-10 col-md-4 col-lg-2 flex-shrink-0">
            <div class="card h-100 border-0 shadow-sm product-card">
              <img
                src="images/product3.png"
                class="card-img-top"
                alt="Product 3"
              />
              <div class="card-body text-center">
                <h5 class="card-title">Basic Tee</h5>
                <p class="card-text text-muted">NPR 1200</p>
                <a
                  href="product3.html"
                  class="btn btn-primary btn-sm stretched-link"
                  >View Details</a
                >
              </div>
            </div>
          </div>
          <!-- Product 4 -->
          <div class="col-10 col-md-4 col-lg-2 flex-shrink-0">
            <div class="card h-100 border-0 shadow-sm product-card">
              <img
                src="images/product4.png"
                class="card-img-top"
                alt="Product 4"
              />
              <div class="card-body text-center">
                <h5 class="card-title">Floral Sleeveless Dress</h5>
                <p class="card-text text-muted">NPR 3500</p>
                <a
                  href="product4.html"
                  class="btn btn-primary btn-sm stretched-link"
                  >View Details</a
                >
              </div>
            </div>
          </div>
          <!-- Product 5 -->
          <div class="col-10 col-md-4 col-lg-2 flex-shrink-0">
            <div class="card h-100 border-0 shadow-sm product-card">
              <img
                src="images/product5.png"
                class="card-img-top"
                alt="Product 5"
              />
              <div class="card-body text-center">
                <h5 class="card-title">Summer Dress</h5>
                <p class="card-text text-muted">NPR 2800</p>
                <a
                  href="product5.html"
                  class="btn btn-primary btn-sm stretched-link"
                  >View Details</a
                >
              </div>
            </div>
          </div>
          <!-- Product 6 -->
          <div class="col-10 col-md-4 col-lg-2 flex-shrink-0">
            <div class="card h-100 border-0 shadow-sm product-card">
              <img
                src="images/product6.png"
                class="card-img-top"
                alt="Product 6"
              />
              <div class="card-body text-center">
                <h5 class="card-title">Silk Dress</h5>
                <p class="card-text text-muted">NPR 3000</p>
                <a
                  href="product6.html"
                  class="btn btn-primary btn-sm stretched-link"
                  >View Details</a
                >
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- /scroll-wrapper -->
    </section>

    <!-- Pagination -->
    <div class="container text-center my-4">
      <nav aria-label="Page navigation example">
        <ul class="pagination justify-content-center">
          <li class="page-item disabled">
            <a class="page-link" href="#" tabindex="-1" aria-disabled="true"
              >&laquo;</a
            >
          </li>
          <li class="page-item">
            <a class="page-link" href="page1.html">1</a>
          </li>
          <li class="page-item">
            <a class="page-link" href="page2.html">2</a>
          </li>
          <li class="page-item">
            <a class="page-link" href="page3.html">3</a>
          </li>
          <li class="page-item">
            <a class="page-link" href="page4.html">4</a>
          </li>
          <li class="page-item">
            <a class="page-link" href="page2.html">&raquo;</a>
          </li>
        </ul>
      </nav>
    </div>

    <!-- Footer -->
    <footer class="ashh-footer text-white pt-5 pb-4">
      <div class="container text-center text-md-start">
        <div class="row text-center text-md-start">
          <div class="col-md-3 col-lg-3 col-xl-3 mx-auto mt-3">
            <h5 class="text-uppercase mb-4 fw-bold text-warning">
              ASHH Clothing
            </h5>
            <p>
              Your one-stop destination for premium fashion. We bring you the
              latest trends at affordable prices.
            </p>
          </div>

          <div class="col-md-4 col-lg-3 col-xl-3 mx-auto mt-3">
            <h5 class="text-uppercase mb-4 fw-bold text-warning">Links</h5>
            <p>
              <a href="about.html" class="text-white text-decoration-none"
                >About Us</a
              >
            </p>
            <p>
              <a href="contact.html" class="text-white text-decoration-none"
                >Contact Us</a
              >
            </p>
          </div>

          <div class="col-md-4 col-lg-3 col-xl-3 mx-auto mt-3">
            <h5 class="text-uppercase mb-4 fw-bold text-warning">Contact Us</h5>
            <p><i class="fas fa-home mr-3"></i> Kathmandu, Nepal</p>
            <p><i class="fas fa-envelope mr-3"></i> contact@ashh.com</p>
            <p><i class="fas fa-phone mr-3"></i> +977 9800000000</p>
          </div>
        </div>

        <hr class="mb-4" />

        <div class="row align-items-center">
          <div class="col-md-7 col-lg-8">
            <p>
              Copyright Â© 2026 All rights reserved by:
              <a href="#" class="text-warning text-decoration-none"
                ><strong>ASHH Clothing</strong></a
              >
            </p>
          </div>
          <div class="col-md-5 col-lg-4">
            <div class="text-center text-md-end">
              <ul class="list-unstyled list-inline">
                <li class="list-inline-item">
                  <a href="#" class="btn-floating btn-sm text-white"
                    ><i class="fab fa-facebook"></i
                  ></a>
                </li>
                <li class="list-inline-item">
                  <a href="#" class="btn-floating btn-sm text-white"
                    ><i class="fab fa-twitter"></i
                  ></a>
                </li>
                <li class="list-inline-item">
                  <a href="#" class="btn-floating btn-sm text-white"
                    ><i class="fab fa-instagram"></i
                  ></a>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </footer>
  </body>
</html>

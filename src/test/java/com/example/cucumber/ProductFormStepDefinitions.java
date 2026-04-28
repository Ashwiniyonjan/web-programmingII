package com.example.cucumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ProductFormStepDefinitions {

    private static final String BASE_URL =
            "http://localhost:8080/SpringMvcHelloWorld";

    private static final String TEST_EMAIL = "testuser@gmail.com";
    private static final String TEST_PASSWORD = "1234";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private String jwtToken;
    private HttpResponse<String> lastResponse;
    private Map<String, Object> lastResponseBody;

    @Before
    public void setUp() throws Exception {

        if (jwtToken != null) return;

        // =========================
        // 1. REGISTER USER (IGNORE IF EXISTS)
        // =========================
        String registerPayload = objectMapper.writeValueAsString(
                Map.of(
                        "email", TEST_EMAIL,
                        "password", TEST_PASSWORD,
                        "firstName", "Test",
                        "lastName", "User",
                        "phone", "9800000000"
                )
        );

        HttpRequest registerRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/users/signup"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(registerPayload))
                .build();

        httpClient.send(registerRequest, HttpResponse.BodyHandlers.ofString());

        // =========================
        // 2. LOGIN USER
        // =========================
        String loginPayload = objectMapper.writeValueAsString(
                Map.of(
                        "email", TEST_EMAIL,
                        "password", TEST_PASSWORD
                )
        );

        HttpRequest loginRequest = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/auth/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(loginPayload))
                .build();

        HttpResponse<String> loginResponse =
                httpClient.send(loginRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println("LOGIN RESPONSE: " + loginResponse.body());

        Map<String, Object> loginBody = objectMapper.readValue(
                loginResponse.body(),
                objectMapper.getTypeFactory()
                        .constructMapType(Map.class, String.class, Object.class)
        );

        jwtToken = (String) loginBody.get("token");

        assertNotNull(jwtToken,
                "Login failed. Check /signup and /login API flow.");
    }

    // =========================
    // PRODUCT TEST
    // =========================

    @Given("the product API is available")
    public void theProductApiIsAvailable() {
        assertNotNull(jwtToken);
    }

    @When("I fill in the product form with the following data:")
    public void iFillInTheProductForm(DataTable dataTable) throws Exception {

        Map<String, String> data = dataTable.asMaps().get(0);

        Map<String, Object> payload = Map.of(
                "name", data.get("name"),
                "price", Double.parseDouble(data.get("price")),
                "description", data.get("description")
        );

        String json = objectMapper.writeValueAsString(payload);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/products/add"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtToken)
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        lastResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("PRODUCT RESPONSE: " + lastResponse.body());

        lastResponseBody = objectMapper.readValue(
                lastResponse.body(),
                objectMapper.getTypeFactory()
                        .constructMapType(Map.class, String.class, Object.class)
        );
    }

    @Then("the product should be added successfully")
    public void productShouldBeAddedSuccessfully() {
        assertEquals(200, lastResponse.statusCode());
    }
}
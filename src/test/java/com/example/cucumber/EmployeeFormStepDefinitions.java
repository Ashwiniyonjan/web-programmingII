package com.example.cucumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeFormStepDefinitions {

    private static final String BASE_URL = "http://localhost:8080/SpringMvcHelloWorld";

    // ⚠️ MUST MATCH YOUR FIXED LOGIN
    private static final String TEST_USERNAME = "admin";
    private static final String TEST_PASSWORD = "1234789";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private String jwtToken;
    private HttpResponse<String> lastResponse;
    private Map<String, Object> lastResponseBody;

    @Before
    public void setUp() throws IOException, InterruptedException {

        if (jwtToken != null) return;

        // =========================
        // STEP 1: LOGIN (FIXED)
        // =========================
        String loginPayload = objectMapper.writeValueAsString(
                Map.of("username", TEST_USERNAME, "password", TEST_PASSWORD)
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
                "Failed to obtain JWT token. Check login API response above.");

    }

    @Given("the employee registration API is available")
    public void theEmployeeRegistrationApiIsAvailable() {
        assertNotNull(jwtToken);
    }

    @When("I fill in the employee form with the following data:")
    public void iFillInTheEmployeeFormWithTheFollowingData(DataTable dataTable)
            throws IOException, InterruptedException {

        Map<String, String> formData = dataTable.asMaps().get(0);

        java.util.HashMap<String, String> payload = new java.util.HashMap<>();
        payload.put("name", formData.getOrDefault("name", ""));
        payload.put("email", formData.getOrDefault("email", ""));
        payload.put("contactNumber", formData.getOrDefault("contactNumber", ""));
        payload.put("position", formData.getOrDefault("position", ""));

        String jsonPayload = objectMapper.writeValueAsString(payload);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/employees"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + jwtToken)
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();

        lastResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("EMP RESPONSE: " + lastResponse.body());

        lastResponseBody = objectMapper.readValue(
                lastResponse.body(),
                objectMapper.getTypeFactory()
                        .constructMapType(Map.class, String.class, Object.class)
        );
    }

    @Then("the employee should be registered successfully")
    public void theEmployeeShouldBeRegisteredSuccessfully() {
        assertEquals(200, lastResponse.statusCode());
        assertEquals("success", lastResponseBody.get("status"));
    }

    @Then("the registration should fail with validation errors")
    public void theRegistrationShouldFailWithValidationErrors() {
        assertEquals(400, lastResponse.statusCode());
    }

    @And("the response should contain the employee name {string}")
    public void theResponseShouldContainTheEmployeeName(String expectedName) {
        Map<String, Object> employee =
                (Map<String, Object>) lastResponseBody.get("employee");

        assertNotNull(employee);
        assertEquals(expectedName, employee.get("name"));
    }

    @And("the error response should contain a message for field {string}")
    public void theErrorResponseShouldContainAMessageForField(String fieldName) {

        Map<String, Object> errors =
                (Map<String, Object>) lastResponseBody.get("errors");

        assertNotNull(errors);
        assertTrue(errors.containsKey(fieldName));
    }
}
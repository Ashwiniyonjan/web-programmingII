package com.example.gatling;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class ProductFormSimulation extends Simulation {

    private final HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080/SpringMvcHelloWorld");

    private final ScenarioBuilder scn = scenario("Basic Test")
            .exec(
                    http("Get Hello")
                            .get("/hello")
                            .check(status().is(200))
            );

    {
        setUp(
                scn.injectOpen(atOnceUsers(1))
        ).protocols(httpProtocol);
    }
}
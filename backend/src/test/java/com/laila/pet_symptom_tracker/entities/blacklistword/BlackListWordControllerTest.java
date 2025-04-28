package com.laila.pet_symptom_tracker.entities.blacklistword;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;

import com.laila.pet_symptom_tracker.mainconfig.Routes;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BlackListWordControllerTest {
  @BeforeAll
  static void setUp() {
    RestAssured.baseURI = "http://localhost:8080";
    RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
  }

  @Test
  public void shouldReturnOkWithListOfBlackListedWords() {}

  @Test
  public void shouldReturnOkWhenPost() {
    // TODO moet ingelogd zijn!
    String payload =
        """
            {
            "word":"Fudgy"
            }
        """;

    Response response =
        given()
            .contentType("application/json")
            .body(payload)
            .when()
            .post(Routes.BLACK_LISTED_WORDS)
            .then()
            .statusCode(201)
            .body(payload, notNullValue())
            .extract()
            .response();
  }
}

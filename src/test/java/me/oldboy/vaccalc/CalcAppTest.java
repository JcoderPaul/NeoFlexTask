package me.oldboy.vaccalc;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CalcAppTest {
    @LocalServerPort
    private Integer localPort;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + localPort;
    }

    @Test
    void contextLoads() {
    }

    @Test
    void shouldReturnVacationIncome_fromTwoParamRequest_statusOkTest() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("avgAmount", "50000")
                .queryParam("vacationDays", "14")
                .when()
                .get("/api/calculacte")
                .then()
                .statusCode(OK.value())
                .body("vacAmount", equalTo(20784.93F));
    }

    @Test
    void shouldReturnVacationIncome_fromThreeParamRequest_statusOkTest() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("avgAmount", "50000")
                .queryParam("vacationDays", "14")
                .queryParam("firstDate", "2024-07-13")
                .when()
                .get("/api/calculacte")
                .then()
                .statusCode(OK.value())
                .body("vacAmount", equalTo(20784.93F));
    }

    @Test
    void shouldReturnError_haveNoParam_statusBadRequestTest() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/calculacte")
                .then()
                .statusCode(BAD_REQUEST.value());
    }
}
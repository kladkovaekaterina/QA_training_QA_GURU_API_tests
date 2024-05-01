package reqresin.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class ReqresInTest {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    void createUser() {

        String data = """
                {
                    "name": "morpheus",
                    "job": "leader"
                }""";

        given()
                .log().uri()
                .log().body()
                .log().headers()
                .body(data)
                .contentType(JSON)

                .when()
                .post("/users")

                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("job", is("leader"));
    }

    @Test
    void deleteUser() {
        given()
                .log().uri()
                .log().body()
                .log().headers()

                .when()
                .delete("/users/2")

                .then()
                .log().status()
                .log().body()
                .statusCode(204)
                .body(emptyOrNullString());
    }

    @Test
    void updateUser() {

        String data = """
                {
                    "name": "morpheus",
                    "job": "zion resident"
                }""";

        given()
                .log().uri()
                .log().body()
                .log().headers()
                .body(data)
                .contentType(JSON)

                .when()
                .patch("/users/2")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("job", is("zion resident"));
    }

    @Test
    void successfulRegistration() {

        String data = """
                {
                    "email": "eve.holt@reqres.in",
                    "password": "pistol"
                }""";

        given()
                .log().uri()
                .log().body()
                .log().headers()
                .body(data)
                .contentType(JSON)

                .when()
                .post("/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", notNullValue())
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void listUsers() {

        List<String> items = List.of("Lawson", "Ferguson", "Funke", "Fields", "Edwards", "Howell");

        given()
                .log().uri()
                .log().body()
                .log().headers()

                .when()
                .get("/users?page=2")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("per_page", is(6))
                .body("data.last_name", hasItems(items.toArray()));
    }

}
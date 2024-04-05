package tests;

import org.junit.jupiter.api.Test;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

public class ReqresInTest {

    @Test
    void createUser() {

        String data = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";

        given()
                .body(data)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/users")

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

                .when()
                .delete("https://reqres.in/api/users/2")

                .then()
                .log().status()
                .log().body()
                .statusCode(204);
    }

    @Test
    void updateUser() {

        String data = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"zion resident\"\n" +
                "}";

        given()
                .body(data)
                .contentType(JSON)
                .log().uri()

                .when()
                .patch("https://reqres.in/api/users/2")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("job", is("zion resident"));;
    }

    @Test
    void successfulRegistration() {

        String data = "{\n" +
                "    \"email\": \"eve.holt@reqres.in\",\n" +
                "    \"password\": \"pistol\"\n" +
                "}";

        given()
                .body(data)
                .contentType(JSON)
                .log().uri()

                .when()
                .post("https://reqres.in/api/register")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void listUsers() {

        List<String> items = List.of("Lawson", "Ferguson", "Funke", "Fields", "Edwards", "Howell");

        given()
                .log().uri()

                .when()
                .get("https://reqres.in/api/users?page=2")

                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("per_page", is(6))
                .body("data.last_name", hasItems(items.toArray()));
    }

}
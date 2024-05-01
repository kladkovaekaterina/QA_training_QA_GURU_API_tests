package reqresin.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import reqresin.models.pojo.*;

import java.util.List;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// with Allure
public class ReqresInWithModelsPojoTest {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    void createUser() {

        UserBodyModel data = new UserBodyModel();
        data.setName("morpheus");
        data.setJob("leader");

        CreateOrUpdateUserResponseModel response = given()
                .filter(withCustomTemplates())
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
                .extract().as(CreateOrUpdateUserResponseModel.class);

        assertEquals("morpheus", response.getName());
        assertEquals("leader", response.getJob());
        assertNotNull(response.getId());
        assertNotNull(response.getCreatedAt());
    }

    @Test
    void deleteUser() {
        given()
                .filter(withCustomTemplates())
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

        UserBodyModel data = new UserBodyModel();
        data.setName("morpheus");
        data.setJob("zion resident");

        CreateOrUpdateUserResponseModel response = given()
                .filter(withCustomTemplates())
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
                .extract().as(CreateOrUpdateUserResponseModel.class);

        assertEquals("morpheus", response.getName());
        assertEquals("zion resident", response.getJob());
        assertNotNull(response.getUpdatedAt());
    }

    @Test
    void successfulRegistration() {

        RegistrationBodyModel data = new RegistrationBodyModel();
        data.setEmail("eve.holt@reqres.in");
        data.setPassword("pistol");

        RegistrationResponseModel response = given()
                .filter(withCustomTemplates())
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
                .extract().as(RegistrationResponseModel.class);

        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
    }

    @Test
    void listUsers() {

        List<String> items = List.of("Lawson", "Ferguson", "Funke", "Fields", "Edwards", "Howell");

        given()
                .filter(withCustomTemplates())
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
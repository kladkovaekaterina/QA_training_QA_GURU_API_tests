package tests;

import io.restassured.RestAssured;
import models.pojo.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static specs.Specs.*;

public class ReqresInWithSpecsTest {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    void createUser() {

        UserBodyModel data = new UserBodyModel();
        data.setName("morpheus");
        data.setJob("leader");

        CreateUserResponseModel response = step("Make request", ()-> given(createUserRequestSpec)
                .body(data)

                .when()
                .post()

                .then()
                .spec(createUserResponseSpec)
                .extract().as(CreateUserResponseModel.class));

        step("Check response", ()-> {
            assertEquals("morpheus", response.getName());
            assertEquals("leader", response.getJob());
            assertNotNull(response.getId());
            assertNotNull(response.getCreatedAt());
        });
    }

    @Test
    void deleteUser() {

        step("Make request", ()-> {
            given(deleteUserRequestSpec)

                    .when()
                    .delete()

                    .then()
                    .spec(deleteUserResponseSpec)
                    .body(emptyOrNullString());
        });
    }

    @Test
    void updateUser() {

        UserBodyModel data = new UserBodyModel();
        data.setName("morpheus");
        data.setJob("zion resident");

        UpdateUserResponseModel response = step("Make request", ()-> given(updateUserRequestSpec)
                .body(data)

                .when()
                .patch()

                .then()
                .spec(status200ResponseSpec)
                .extract().as(UpdateUserResponseModel.class));

        step("Check response", ()-> {
            assertEquals("morpheus", response.getName());
            assertEquals("zion resident", response.getJob());
            assertNotNull(response.getUpdatedAt());
        });
    }

    @Test
    void successfulRegistration() {

        RegistrationBodyModel data = new RegistrationBodyModel();
        data.setEmail("eve.holt@reqres.in");
        data.setPassword("pistol");

        RegistrationResponseModel response = step("Make request", ()-> given(registrationRequestSpec)
                .body(data)

                .when()
                .post()

                .then()
                .spec(status200ResponseSpec)
                .extract().as(RegistrationResponseModel.class));

        step("Check response", ()-> assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));
    }

    @Test
    void listUsers() {

        List<String> items = List.of("Lawson", "Ferguson", "Funke", "Fields", "Edwards", "Howell");

        step("Make request", ()-> {
            given(listUsersRequestSpec)

                    .when()
                    .get()

                    .then()
                    .spec(status200ResponseSpec)
                    .body("per_page", is(6))
                    .body("data.last_name", hasItems(items.toArray()));
        });
    }

}
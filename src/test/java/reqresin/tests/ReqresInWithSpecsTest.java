package reqresin.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reqresin.models.pojo.*;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static reqresin.specs.Specs.*;

public class ReqresInWithSpecsTest {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    @DisplayName("Проверка создания пользователя")
    void createUser() {

        UserBodyModel data = new UserBodyModel();
        data.setName("morpheus");
        data.setJob("leader");

        CreateOrUpdateUserResponseModel response = step("Make request", ()-> given(createOrUpdateUserRequestSpec)
                .body(data)

                .when()
                .post("/users")

                .then()
                .spec(createUserResponseSpec)
                .extract().as(CreateOrUpdateUserResponseModel.class));

        step("Check response", ()-> {
            assertEquals("morpheus", response.getName());
            assertEquals("leader", response.getJob());
            assertNotNull(response.getId());
            assertNotNull(response.getCreatedAt());
        });
    }

    @Test
    @DisplayName("Проверка удаления пользователя")
    void deleteUser() {

        step("Make request", ()-> {
            given(deleteUserRequestSpec)

                    .when()
                    .delete("/users/2")

                    .then()
                    .spec(deleteUserResponseSpec)
                    .body(emptyOrNullString());
        });
    }

    @Test
    @DisplayName("Проверка апдейта данных для пользователя")
    void updateUser() {

        UserBodyModel data = new UserBodyModel();
        data.setName("morpheus");
        data.setJob("zion resident");

        CreateOrUpdateUserResponseModel response = step("Make request", ()-> given(createOrUpdateUserRequestSpec)
                .body(data)

                .when()
                .patch("/users/2")

                .then()
                .spec(status200ResponseSpec)
                .extract().as(CreateOrUpdateUserResponseModel.class));

        step("Check response", ()-> {
            assertEquals("morpheus", response.getName());
            assertEquals("zion resident", response.getJob());
            assertNotNull(response.getUpdatedAt());
        });
    }

    @Test
    @DisplayName("Проверка успешной регистрации пользователя")
    void successfulRegistration() {

        RegistrationBodyModel data = new RegistrationBodyModel();
        data.setEmail("eve.holt@reqres.in");
        data.setPassword("pistol");

        RegistrationResponseModel response = step("Make request", ()-> given(registrationRequestSpec)
                .body(data)

                .when()
                .post("/register")

                .then()
                .spec(status200ResponseSpec)
                .extract().as(RegistrationResponseModel.class));

        step("Check response", ()-> assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));
    }

    @Test
    @DisplayName("Проверка фамилий пользователей, которые содержатся на 2 странице")
    void listUsers() {

        List<String> items = List.of("Lawson", "Ferguson", "Funke", "Fields", "Edwards", "Howell");

        step("Make request", ()-> {
            given(listUsersRequestSpec)

                    .when()
                    .get("/users?page=2")

                    .then()
                    .spec(status200ResponseSpec)
                    .body("per_page", is(6))
                    .body("data.last_name", hasItems(items.toArray()));
        });
    }

}
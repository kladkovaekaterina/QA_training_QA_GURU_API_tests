package demoqa.api;

import demoqa.models.BookDataModel;
import demoqa.models.ResponseModel;
import demoqa.models.UserDataModel;
import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static demoqa.specs.Specs.*;
import static demoqa.tests.TestData.password;
import static demoqa.tests.TestData.userName;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Api {

    ResponseModel responseModel = makeAuthApiPostRequest();

    public String token = responseModel.getToken(),
                  username = responseModel.getUsername(),
                  userId = responseModel.getUserId(),
                  expires = responseModel.getExpires();

    @Step("Make authorization request")
    public ResponseModel makeAuthApiPostRequest() {
        UserDataModel userData = new UserDataModel();
        userData.setUserName(userName);
        userData.setPassword(password);

        return given(requestUserAuthSpec)
                .body(userData)

                .when()
                .post("/Account/v1/Login")

                .then()
                .spec(responseSpec(200))
                .extract().as(ResponseModel.class);
    }

    @Step("Check authorization response")
    public Api checkAuthApiResponse() {
        assertEquals(userName, username);
        assertNotNull(token);

        return this;
    }

    @Step("Set cookie")
    public Api setCookie() {
        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", userId));
        getWebDriver().manage().addCookie(new Cookie("expires", expires));
        getWebDriver().manage().addCookie(new Cookie("token", token));

        return this;
    }

    @Step("Make request to delete all books")
    public Api makeDeleteAllBooksRequest() {
        given(requestBookSpec(token))
                .queryParams("UserId", userId)

                .when()
                .delete("/BookStore/v1/Books")

                .then()
                .spec(responseSpec(204));

        return this;
    }

    @Step("Make request to add a book")
    public void makeBookPostRequest(String isbn) {
        BookDataModel bookData = new BookDataModel();

        given(requestBookSpec(token))
                .body(bookData.getBookDataRequest(userId, isbn))

                .when()
                .post("/BookStore/v1/Books")

                .then()
                .spec(responseSpec(201));
    }
}
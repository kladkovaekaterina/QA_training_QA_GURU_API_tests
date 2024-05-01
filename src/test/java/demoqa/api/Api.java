package demoqa.api;

import demoqa.models.BooksModel;
import demoqa.models.ResponseModel;
import demoqa.models.UserModel;

import static demoqa.specs.Specs.*;
import static demoqa.tests.TestData.password;
import static demoqa.tests.TestData.userName;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Api {

    public ResponseModel makeAuthApiPostRequest() {
        UserModel data = new UserModel();
        data.setUserName(userName);
        data.setPassword(password);

        ResponseModel responseLogin = given(requestSpec)
                .body(data)

                .when()
                .post("/Account/v1/Login")

                .then()
                .spec(loginResponseSpec)
                .extract().as(ResponseModel.class);

        return responseLogin;
    }

    public void checkAuthApiResponse(String userName, ResponseModel responseLogin) {
        assertEquals(userName, responseLogin.getUsername());
        assertNotNull(responseLogin.getToken());
    }

    public void makeDeleteAllBooksRequest() {
        given(requestSpec)
                .header("Authorization", "Bearer " + makeAuthApiPostRequest().getToken())
                .queryParams("UserId", makeAuthApiPostRequest().getUserId())

                .when()
                .delete("/BookStore/v1/Books")

                .then()
                .spec(deleteBooksResponseSpec);
    }

    public void makeBookPostRequest() {
        BooksModel bookData = new BooksModel();
        bookData.setIsbn("9781449365035");
        String bookDataRequest = format("{\"userId\":\"%s\",\"collectionOfIsbns\":[{\"isbn\":\"%s\"}]}",
                makeAuthApiPostRequest().getUserId() , bookData.getIsbn());

        given(requestSpec)
                .header("Authorization", "Bearer " + makeAuthApiPostRequest().getToken())
                .body(bookDataRequest)

                .when()
                .post("/BookStore/v1/Books")

                .then()
                .spec(addBooksResponseSpec);
    }
}
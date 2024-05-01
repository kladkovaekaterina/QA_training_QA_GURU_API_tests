package demoqa.tests;

import demoqa.api.Api;
import demoqa.pages.ProfilePage;
import org.junit.jupiter.api.*;

import static demoqa.tests.TestData.userName;
import static io.qameta.allure.Allure.step;

@Tag("demoqa")
public class DemoqaTest extends TestBase {

    ProfilePage profilePage = new ProfilePage();
    Api api = new Api();

    @Test
    @DisplayName("Проверка удаления товара из списка книг")
    void deleteTest() {

        step("Make authorization request", ()-> {
            api.makeAuthApiPostRequest();
        });

        step("Check authorization response", ()-> {
            api.checkAuthApiResponse(userName, api.makeAuthApiPostRequest());
        });

        step("Set cookie", () -> {
            profilePage.openRandomPageForCookieSetUp()
                       .setCookie(api.makeAuthApiPostRequest());
        });

        step("Make request to delete all books", ()-> {
            api.makeDeleteAllBooksRequest();
        });

        step("Make request to add a book", ()-> {
            api.makeBookPostRequest();
        });

        step("Open profile page", () -> {
            profilePage.openProfilePage();
        });

        step("Check successful login", () -> {
            profilePage.checkLoginResult();
        });

        step("Check books were added", () -> {
            profilePage.checkBooksWereAdded();
        });

        step("Delete books", () -> {
            profilePage.deleteBooks();
        });

        step("Check books were deleted", () -> {
            profilePage.checkBooksWereDeleted();
        });
    }
}
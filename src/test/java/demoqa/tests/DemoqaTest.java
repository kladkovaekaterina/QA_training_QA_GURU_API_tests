package demoqa.tests;

import demoqa.api.Api;
import demoqa.pages.ProfilePage;
import org.junit.jupiter.api.*;

@Tag("demoqa")
public class DemoqaTest extends TestBase {

    ProfilePage profilePage = new ProfilePage();
    Api api = new Api();

    @Test
    @DisplayName("Проверка удаления товара из списка книг")
    void deleteTest() {
        api.makeAuthApiPostRequest();
        api.checkAuthApiResponse()
           .setCookie()
           .makeDeleteAllBooksRequest()
           .makeBookPostRequest("9781449365035");
        profilePage.openProfilePage()
           .checkLoginResult()
           .checkBooksWereAdded()
           .deleteBooks()
           .checkBooksWereDeleted();
    }
}
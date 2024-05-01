package demoqa.pages;

import com.codeborne.selenide.SelenideElement;
import demoqa.models.ResponseModel;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static demoqa.tests.TestData.userName;

public class ProfilePage {

    private final SelenideElement loginValue = $("#userName-value"),
                                  deleteBooks = $("#delete-record-undefined"),
                                  confirmDeleteBooks = $("#closeSmallModal-ok"),
                                  booksList = $(".ReactTable");


    public ProfilePage openRandomPageForCookieSetUp() {
        open("/favicon.ico");

        return this;
    }

    public ProfilePage setCookie(ResponseModel responseLogin) {
        getWebDriver().manage().addCookie(new Cookie("userID", responseLogin.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("expires", responseLogin.getExpires()));
        getWebDriver().manage().addCookie(new Cookie("token", responseLogin.getToken()));

        return this;
    }

    public ProfilePage openProfilePage() {
        open("/profile");

        return this;
    }

    public ProfilePage deleteBooks() {
        deleteBooks.click();
        confirmDeleteBooks.click();

        return this;
    }

    public ProfilePage checkLoginResult() {
        loginValue.shouldHave(text(userName));

        return this;
    }

    public ProfilePage checkBooksWereAdded() {
        booksList.shouldHave(text("Speaking JavaScript"));

        return this;
    }

    public ProfilePage checkBooksWereDeleted() {
        booksList.shouldNotHave(text("Speaking JavaScript"));

        return this;
    }
}
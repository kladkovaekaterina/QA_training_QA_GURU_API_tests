package demoqa.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static demoqa.tests.TestData.userName;

public class ProfilePage {

    private final SelenideElement loginValue = $("#userName-value"),
                                  deleteBooks = $("#delete-record-undefined"),
                                  confirmDeleteBooks = $("#closeSmallModal-ok"),
                                  booksList = $(".ReactTable");

    @Step("Open profile page")
    public ProfilePage openProfilePage() {
        open("/profile");

        return this;
    }

    @Step("Delete books")
    public ProfilePage deleteBooks() {
        deleteBooks.click();
        confirmDeleteBooks.click();

        return this;
    }

    @Step("Check successful login")
    public ProfilePage checkLoginResult() {
        loginValue.shouldHave(text(userName));

        return this;
    }

    @Step("Check books were added")
    public ProfilePage checkBooksWereAdded() {
        booksList.shouldHave(text("Speaking JavaScript"));

        return this;
    }

    @Step("Check books were deleted")
    public void checkBooksWereDeleted() {
        booksList.shouldNotHave(text("Speaking JavaScript"));
    }
}
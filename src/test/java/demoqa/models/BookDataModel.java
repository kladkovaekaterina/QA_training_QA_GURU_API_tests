package demoqa.models;

import static java.lang.String.format;

public class BookDataModel {

    String bookDataRequest;

    public void setBookDataRequest(String bookDataRequest) {
        this.bookDataRequest = bookDataRequest;
    }

    public String getBookDataRequest(String userId, String isbn) {
        return bookDataRequest = format("{\"userId\":\"%s\",\"collectionOfIsbns\":[{\"isbn\":\"%s\"}]}",
                userId, isbn);
    }
}
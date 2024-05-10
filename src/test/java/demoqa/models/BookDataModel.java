package demoqa.models;

import lombok.Data;

import java.util.List;

@Data
public class BookDataModel {

    String userId;
    List<CollectionOfIsbns> collectionOfIsbns;

    @Data
    public static class CollectionOfIsbns {
        String isbn;
    }

}
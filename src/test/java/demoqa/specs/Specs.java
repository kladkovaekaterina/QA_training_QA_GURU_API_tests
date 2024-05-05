package demoqa.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

public class Specs {

    public static RequestSpecification requestUserAuthSpec = with()
            .filter(withCustomTemplates())
            .log().uri()
            .log().method()
            .contentType(JSON);

    public static RequestSpecification requestBookSpec(String token) {
        return with()
                .filter(withCustomTemplates())
                .header("Authorization", "Bearer " + token)
                .log().uri()
                .log().body()
                .log().method()
                .log().headers()
                .contentType(JSON);
    }

    public static ResponseSpecification responseSpec(int statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .log(STATUS)
                .log(BODY)
                .build();
    }
}
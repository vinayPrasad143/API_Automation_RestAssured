package org.example;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class Handlingstaticjsonfile {

    @Test
    public void staticJson() throws IOException {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String Response = given()
                .log().all()
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(new String(Files.readAllBytes(Paths.get("D:\\API_Automation_Learning\\src\\main\\java\\files\\Google+Place+APIs.postman_collection.json"))))
                .when()
                .post("/maps/api/place/add/json")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();
        System.out.println(Response);
    }
}

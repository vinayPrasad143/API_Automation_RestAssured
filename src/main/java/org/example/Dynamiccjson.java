package org.example;

import files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Dynamiccjson {

    @Test(priority = 0, dataProvider ="Booksdata")
    public void addBooks(String isbn, String aisle, String author){

        RestAssured.baseURI = "http://216.10.245.166";
        String response = given()
                .log().all()
                .header("Content-Type", "application/json")
                .body(Payload.addBook(isbn,aisle,author))
                .when()
                .post("/Library/Addbook.php")
                .then()
                .log().all()
                .assertThat().statusCode(200).body("Msg", equalTo("successfully added"))
                .extract().response().asString();

        JsonPath js1=  ReUsableMethods.rawToJson(response);
        String id = js1.get("ID");
        System.out.println("id : " + id);
    }

    @DataProvider(name="Booksdata")
    public Object[][] getData(){

        return new Object[][] {
                {"Vinay","03928", "Vinayprasad"}, {"Vihaan", "08098", "Vihaanprasad"}, {"Chandana", "24068", "Chandanaprasad"}
        };

    }
}

package org.example;

import files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DynamicJson {

    @Test(priority = 0)
    public void addBook()
    {
        RestAssured.baseURI = "http://216.10.245.166";
        String response = given().header("Content-Type", "application/json").body(Payload.addBook())
                .when()
                .post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();
                JsonPath js = ReUsableMethods.rawToJson(response);
                String ID = js.get("ID");
        System.out.println(ID);
    }

    @Test(priority = 1)
    public void GetBookByAuthor(){
        RestAssured.baseURI = "http://216.10.245.166";
        String response1 = given()
                .log().all()
                .queryParam("AuthorName", "vinayprasad")
                .when()
                .get("/Library/GetBook.php")
                .then().log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();
        JsonPath js2 = ReUsableMethods.rawToJson(response1);
        String Bookname = js2.getString("book_name");
        System.out.println("BookName : " + Bookname);
    }

    @Test(priority = 2)
    public void GetBookByID()
    {
        RestAssured.baseURI="http://216.10.245.166";
        String response2 = given()
                .log().all()
                .queryParam("ID", "vin3292")
                .when()
                .get("/Library/GetBook.php")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();
       JsonPath js3 = ReUsableMethods.rawToJson(response2);
       String bookname = js3.getString("book_name");
        System.out.println("bookname : " + bookname);
    }

    @Test(priority = 3)
    public void DeleteBookById()
    {
        RestAssured.baseURI = "http://216.10.245.166";
        given()
                .log().all()
                .header("Content-Type", "application/json").body("{\n" +
                        " \n" +
                        "\"ID\" : \"vin3292\"\n" +
                        " \n" +
                        "} ")
                .when()
                .post("/Library/DeleteBook.php")
                .then()
                .log().all()
                .assertThat().statusCode(200);

    }

}

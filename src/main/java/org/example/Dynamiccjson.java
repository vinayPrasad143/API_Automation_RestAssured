package org.example;

import files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Dynamiccjson {

    @DataProvider(name="Booksdata")
    public Object[][] getAddBookData(){

        return new Object[][] {
                {"Vinay","03928", "Vinayprasad"},
                {"Vihaan", "08098", "Vihaanprasad"},
                {"Chandana", "24068", "Chandanaprasad"}
        };

    }

    @DataProvider(name = "DeleteBooksData")
    public Object[][] getDeleteData() {
        return new Object[][] {
                {"Vinay", "03928"},
                {"Vihaan", "08098"},
                {"Chandana", "24068"}
        };
    }

    @Test(priority = 1, dataProvider ="Booksdata")
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

    @Test(dataProvider = "DeleteBooksData", priority=1)
    public void getBooksData(String isbn, String aisle)
    {
        RestAssured.baseURI ="http://216.10.245.166";
        String bookId = isbn + aisle;
        String response2 = given()
                .log().all()
                .queryParam("ID", bookId)
                .when()
                .get("/Library/GetBook.php")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .extract().response().asString();
        JsonPath js3 = ReUsableMethods.rawToJson(response2);
           String Bookname = js3.getString("book_name");
           String isbnNo = js3.getString("isbn");
           String aisleNo = js3.getString("aisle");
            String author = js3.getString("author");

        System.out.println("book_name : " + Bookname + "isbn : " + isbnNo + "aisle" + aisleNo + "AUthor : " + author);
    }

    @Test(priority = 2, dataProvider = "DeleteBooksData")
    public void deleteBooks(String isbn, String aisle){

        RestAssured.baseURI = "http://216.10.245.166";
        String response1 = given()
                .log().all()
                .header("Content-Type","application/json")
                .body(Payload.deleteBook(isbn+aisle))
                .when()
                .post("/Library/DeleteBook.php")
                .then()
                .log().all()
                .assertThat().statusCode(200).body("msg", equalTo("book is successfully deleted"))
                .extract().response().asString();
    }
}

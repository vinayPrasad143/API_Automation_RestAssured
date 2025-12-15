package org.example;

import files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class API_Basics {

    public static void main(String[] args) {

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        // update the place
        String response = given()
                .log().all()
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(Payload.AddPlace())
                .when()
                .post("/maps/api/place/add/json")
                .then()
                .assertThat().statusCode(200)
                .body("scope", equalTo("APP"))
                .header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
        System.out.println(response);

        JsonPath js = new JsonPath(response);
        String placeId = js.getString("place_id");
        System.out.println("Place_ID = " + placeId);

        // update the place
        String newAddress = "70 Summer walk, UK";
        given()
                .log().all()
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "\"place_id\":\""+ placeId +"\",\n" +
                        "\"address\":\"" + newAddress +"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}")
                .when().put("/maps/api/place/update/json")
                .then()
                .log().all()
                .assertThat().statusCode(200)
                .body("msg", equalTo("Address successfully updated"));


        //get the updated place
        String getPlaceResponse = given()
                .log().all()
                .queryParam("key", "qaclick123")
                .queryParam("place_id", placeId)
                .when().get("/maps/api/place/get/json")
                .then().assertThat().log().all()
                .statusCode(200).extract().response().asString();

        JsonPath js1 = new JsonPath(getPlaceResponse);
        String actualAddress = js1.getString("address");
        System.out.println("ActualAddress = " + actualAddress);

        //Delete the Place
        given()
                .log().all()
                .queryParam("key", "qaclick123")
                .body("{\n" +
                        "    \"place_id\": \"" + placeId +"\"\n" +
                        "}")
                .when()
                .delete("/maps/api/place/delete/json")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("status", equalTo("OK"));
        System.out.println("The place is deleted");


        // validate whether the place is deleted or not
        given()
                .log().all()
                .queryParam("key", "qaclick123")
                .queryParam("place_id", placeId)
                .when().get("/maps/api/place/get/json")
                .then().assertThat().log().all()
                .statusCode(404)
                .body("msg", equalTo("Get operation failed, looks like place_id  doesn't exists"));

        System.out.println("The place is deleted successfully");

        //Validate whether the deleted user try to delete once again

        given()
                .log().all()
                .queryParam("key", "qaclick123")
                .body("{\n" +
                        "    \"place_id\": \"" + placeId +"\"\n" +
                        "}")
                .when()
                .delete("/maps/api/place/delete/json")
                .then()
                .log().all()
                .assertThat()
                .statusCode(404)
                .body("msg", equalTo("Delete operation failed, looks like the data doesn't exists"));
        System.out.println("unabled to delete the already deleted place");


    }
}

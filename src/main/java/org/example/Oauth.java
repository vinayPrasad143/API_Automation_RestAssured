package org.example;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;


public class Oauth {

    @Test
    public void authentication()
    {
     String response =  given()
              .log().all()
              .formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
              .formParam("client_secret","erZOWM9g3UtwNRj340YYaK_W")
              .formParam("grant_type","client_credentials")
              .formParam("grant_type","client_credentials")
              .formParam("scope","trust")
              .when()
              .log().all()
              .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
        System.out.println(response);
        JsonPath js1 = new JsonPath(response);
        String accessToken = js1.getString("access_token");

        String response2 = given()
                .queryParam("access_token", accessToken)
                .when()
                .log().all()
                .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
                .then()
                .log().all()
                .extract().response().asString();
        System.out.println(response2);

        JsonPath js2 = new JsonPath(response2);
        String instructorName = js2.getString("instructor");
        String expertise = js2.getString("expertise");
        System.out.println( instructorName + ":" + expertise);

    }





}

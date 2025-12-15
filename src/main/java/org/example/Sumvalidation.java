package org.example;

import files.Payload;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

public class Sumvalidation {

@Test
    public void sumOfCourses()
{
    int totalAmount =0;
    JsonPath js = new JsonPath(Payload.jsonPath());
    int coursesCount = js.getInt("courses.size()");
    for(int i=0; i<coursesCount; i++)
    {
        String Title = js.getString("courses["+i+"].title");
        int price = js.getInt("courses["+i+"].price");
        int copies = js.getInt("courses["+i+"].copies");
        int amount = price*copies;
        totalAmount += amount;
        System.out.println(Title + ":" + amount);
    }
    System.out.println("Total Amount :" + totalAmount);
}
}

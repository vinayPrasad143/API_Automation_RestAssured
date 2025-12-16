package org.example;

import files.Payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
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

    int purchasedAmount = js.get("dashboard.purchaseAmount");
    System.out.println("Purchased Amount :" + purchasedAmount);
//    if (purchasedAmount == totalAmount)
//    {
//        System.out.println("Amount purchased is same as total amount calculated");
//    }
//    else {
//        System.out.println("Amount purchased is not same as total amount calculated");
//    }
    Assert.assertEquals(totalAmount, purchasedAmount);
}
}

package org.example;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonPath {

    public static void main(String[] args) {

        JsonPath js = new JsonPath(Payload.jsonPath());

        //Print the number of courses present

        int count = js.getInt("courses.size()");
        System.out.println("number of courses present : " + count );

        // To get the purchase amount

        int count2 = js.getInt("dashboard.purchaseAmount");

        System.out.println("Purchase Amount : " + count2);

        // To get the first course name

        String firstCourseName = js.get("courses[0].title");

        System.out.println("First course name : " + firstCourseName);

        // To get the last course price

        int lastCoursePrice = js.getInt("courses[2].price");

        System.out.println("Last course price : " + lastCoursePrice);

        //Print all the courses and their prices

        for(int i=0; i<count; i++)
        {
            String CourseName = js.getString("courses["+i+"].title");
           int coursePrice = js.getInt("courses["+i+"].price");
            System.out.println(CourseName + " : " + coursePrice);
        }

        System.out.println("Print number of copies sold by RPA");

        for(int i=0; i<count; i++)
        {
            String courseTitle = js.get("courses["+i+"].title");
            if (courseTitle.equalsIgnoreCase("RPA"))
            {
                int copies = js.get("courses["+i+"].copies");
                System.out.println("RPA : " + copies);
                break;
            }

        }

    }






}

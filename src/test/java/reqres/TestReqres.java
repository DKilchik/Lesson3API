package reqres;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;



public class TestReqres {

    @Test
    public void testUserList(){


        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void testCreateUser(){

        given()
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}")
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .assertThat()
                .statusCode(201)
                .assertThat()
                .body(containsString("id"));
    }

    @Test
    public void testGetSingleUser(){

        given()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .assertThat()
                .statusCode(200)
                .assertThat()
                .body("data.id",equalTo(2)).assertThat().body(matchesJsonSchemaInClasspath("schemas"));
    }

    @Test
    public void testGetSingleResource(){

        given()
                .when()
                .get("https://reqres.in/api/unknown/2")
                .then()
                .assertThat()
                .statusCode(200)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/unknown.json"));
    }


}

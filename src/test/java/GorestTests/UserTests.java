package GorestTests;

import io.restassured.RestAssured;
import org.codehaus.groovy.util.ListHashMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserTests {
    @BeforeAll
    static void config() {
        RestAssured.baseURI = "https://gorest.co.in";
        RestAssured.basePath = "/public/v2/users";
    }

    @Test
    void ItShouldReturnSpecificActiveUser() {
        given().pathParam("id", "2723")
                .when().get("/{id}")
                .then().statusCode(200).statusLine(containsString("OK"))
                .body("name", equalTo("Deepali Dhawan"))
                .body("gender", oneOf("male", "female"))
                .body("email", not(emptyOrNullString()))
                .body("status", is("active"));

    }

    @Test
    void ItShouldReturnErrorMessageWhenUserIsNotFound() {
        given().pathParam("id", "2729")
                .when().get("/{id}")
                .then().log().status().body("message", equalTo("Resource not found"))
                .statusCode(404);
    }

    @Test
    void ItShouldReturnAllUsers() {
        when().get().then().log().body();
    }

    @Test
    void ItShouldReturnStatusForEachUser() {
        List<HashMap<Object, Object>> ActiveUsers = when().get()
                .then().extract().response()
                .jsonPath().getList("$");
        ActiveUsers.forEach(name -> System.out.println(name.get("status")));
    }

    @Test
    void ItShouldReturnIfEachUserHasStatusAndGender2() {
        List<HashMap<Object, Object>> ActiveUsers = when().get()
                .then().extract().response()
                .jsonPath().getList("$");
        ActiveUsers.forEach(name -> {
            assertThat(name.get("status").toString(), not(emptyOrNullString()));
            assertThat(name.get("gender").toString(), not(emptyOrNullString()));
        });
    }

    @Test
    void ItShouldReturnExactNumberOfUsers() {
        when().get().then().extract().response()
                .jsonPath().getList("$");
        List<HashMap<Object, Object>> Users = when().get()
                .then().extract().response()
                .jsonPath().getList("$");
        assertThat(Users, hasSize(20));
    }

    @Test
    void GetUserSize() {
        int size = when().get().then().extract().response().jsonPath().getList("").size();
        assertThat(size, is(20));
    }

    @Test
        //object and second is String. It is not needed to use "toString()" then
    void ItShouldReturnIfEachUserHasStatusAndGender() {
        List<HashMap<Object, String>> ActiveUsers = when().get()
                .then().extract().response()
                .jsonPath().getList("$");
        ActiveUsers.forEach(name -> {
            assertThat(name.get("status"), not(emptyOrNullString()));
            assertThat(name.get("gender"), not(emptyOrNullString()));
        });
    }
}

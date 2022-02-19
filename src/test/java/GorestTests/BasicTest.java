package GorestTests;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class BasicTests {
    @Test
    void itShoulReturnStatusCode200(){
        given().baseUri("https://gorest.co.in")
                .when().get()
                .then().statusCode(200);
    }

    @Test
    void ItShouldReturnSpecificUserWithGenderMale(){
        given().baseUri("https://gorest.co.in/public/v2/users/2693")
                .when().get()
                .then().log().body()
                .and().body("gender",oneOf("male","female"));
    }
    @Test
    void ItShouldContainEmail(){
        given().baseUri("https://gorest.co.in/public/v2/users/2640")
                .when().get()
                .then().log().body()
                .body("email",not(emptyOrNullString()));
    }

    @Test
    void itShouldReturnOneSpecificUser() {
        given().baseUri("https://gorest.co.in/public/v2/users/2737")
                .when().get()
                .then().statusCode(200)
                .body("name", equalTo("Prof. Jaya Mukhopadhyay"))
                .log().body();
    }

}
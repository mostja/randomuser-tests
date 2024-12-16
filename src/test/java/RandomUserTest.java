import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

public class RandomUserTest {
    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "https://randomuser.me/api/";
    }

    private Response getRandomUser() {
        return when().get().then().statusCode(200).extract().response();
    }
    private void validateThatResultHasSingleValue(Response response) {
        response.then()
                .body("results", notNullValue())
                .body("results.size()", equalTo(1)); 
    }

    @Test
    public void shouldReturnSuccessToBaseURICall() {
        when().get().then().statusCode(200);
    }

    @Test
    public void shouldBeOlderThan40() {
        Response response = getRandomUser();
        validateThatResultHasSingleValue(response);
        response.then().body("results[0].dob.age", greaterThan(40));
    }

    @Test
    public void shouldComeFromUS() {
        Response response = getRandomUser();
        validateThatResultHasSingleValue(response);
        response.then().body("results[0].nat", equalTo("US"));
    }

    @Test
    public void shouldValidatePasswordPattern() {
        String passwordPattern = "^[a-zA-Z0-9]+$";

        Response response = getRandomUser();
        validateThatResultHasSingleValue(response);
        response.then().body("results[0].login.password", matchesPattern(passwordPattern));
    }

}

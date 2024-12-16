import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

public class RandomUserTest {

    public static final String AGE_PATH = "results[0].dob.age";
    public static final String NATIONALITY_PATH = "results[0].nat";
    public static final String PASSWORD_PATH = "results[0].login.password";
    public static final String PASSWORD_PATTERN = "^[a-zA-Z0-9]+$";

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
        response.then().body(AGE_PATH, greaterThan(40));
    }

    @Test
    public void shouldComeFromUS() {
        Response response = getRandomUser();
        validateThatResultHasSingleValue(response);
        response.then().body(NATIONALITY_PATH, equalTo("US"));
    }

    @Test
    public void shouldValidatePasswordPattern() {
        Response response = getRandomUser();
        validateThatResultHasSingleValue(response);
        response.then().body(PASSWORD_PATH, matchesPattern(PASSWORD_PATTERN));
    }

    @Test
    public void shouldValidateSpecificFields() {
        Response response = getRandomUser();
        validateThatResultHasSingleValue(response);
        response.then().body(PASSWORD_PATH, matchesPattern(PASSWORD_PATTERN),
                AGE_PATH, greaterThan(40),
                NATIONALITY_PATH, equalTo("US")
        );
    }

}

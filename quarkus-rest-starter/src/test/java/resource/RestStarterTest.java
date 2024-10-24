package resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class RestStarterTest {
//    @Test
//    void testHelloEndpoint() {
//        given()
//          .when().get("/started")
//          .then()
//             .statusCode(200)
//             .body(is("Started Quarkus REST application at port 7070"));
//    }

    @Test
    public void testHelloEndpoint() {
        given()
                .contentType("application/json")
                .body("{\"message\": \"Hello\"}") // Ensure you send a valid JSON body
                .when()
                .post("/started") // Use POST here
                .then()
                .statusCode(200); // Expect a 200 response
    }

}
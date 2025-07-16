package dev.ryanlioy.restassured.test;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

public class User {
    private static final Header AUTH_HEADER = APIKey.getAuthHeader();

    @BeforeAll
    public static void beforeAll() {
        RestAssured.baseURI = "https://reqres.in/api";
    }

    @Test
    public void getUser() {
        given().when()
                .header(AUTH_HEADER)
                .get("/users/1")
                .then()
                .body("data.id", equalTo(1))
                .body("data.first_name", equalTo("George"))
                .body("data.last_name", equalTo("Bluth"));
    }

    @Test
    public void getUsers() {
        given().when()
                .header(AUTH_HEADER)
                .get("/users?page=1")
                .then()
                .statusCode(200)
                .body("page", equalTo(1))
                .body("data", hasSize(6))
                .body("data[0].id", equalTo(1));
    }

    @Test
    public void getUser_userNotFound() {
        given().when()
                .header(AUTH_HEADER)
                .get("/users/20")
                .then()
                .statusCode(404)
                .body("isEmpty()", is(true));
    }

    @Test
    public void createUser() {
        given()
                .header("Content-type", "application/json")
                .header(AUTH_HEADER)
                .body(
                      """
                      {
                        "name": "name",
                        "job": "job"
                      }
                      """
                )
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .body("id", not(nullValue()))
                .body("name", equalTo("name"))
                .body("job", equalTo("job"));
    }

    @Test
    public void updateUser() {
     given()
             .header("Content-type", "application/json")
             .header(AUTH_HEADER)
             .body(
                   """
                   {
                       "name": "name",
                       "job": "job"
                   }
                   """)
             .put("/users/1")
             .then()
             .statusCode(200)
             .body("name", equalTo("name"))
             .body("job", equalTo("job"));
    }

    @Test
    public void deleteUser() {
        given()
                .header("Content-type", "application/json")
                .header(AUTH_HEADER)
                .delete("/users/1")
                .then()
                .statusCode(204);
    }
}
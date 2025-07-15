package dev.ryanlioy.restassured.test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
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
}
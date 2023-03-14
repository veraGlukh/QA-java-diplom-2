package ru.yandex.practicum.burgers;

import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class UserClient extends Client {

    private static final String USER_CREATE_PATH = "/api/auth/register";
    private static final String USER_LOGIN_PATH = "/api/auth/login";
    private static final String USER_DELETE_PATH = "/api/auth/user";
    private static final String USER_CHANGE_PATH = "/api/auth/user";

    public ValidatableResponse create(User user) {
        return given()
                .spec(getSpec())
                .body(user)
                .when()
                .post(USER_CREATE_PATH)
                .then();
    }

    public ValidatableResponse login(UserCredentials UserCredentials) {
        return given()
                .spec(getSpec())
                .body(UserCredentials)
                .when()
                .post(USER_LOGIN_PATH)
                .then();
    }

    public ValidatableResponse delete(String accessToken) {
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .delete(USER_DELETE_PATH)
                .then();
    }

    public ValidatableResponse change(String accessToken, User user) {
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(USER_CHANGE_PATH)
                .then();
    }
}

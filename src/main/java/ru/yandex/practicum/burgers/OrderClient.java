package ru.yandex.practicum.burgers;

import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class OrderClient extends Client {

    private static final String ORDER_CREATE_PATH = "/api/orders";
    private static final String USER_ORDER_LIST_PATH = "/api/orders";
    private static final String INGREDIENT_LIST_PATH = "/api/ingredients";

    public ValidatableResponse create(String accessToken, Order order) {
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post(ORDER_CREATE_PATH)
                .then();
    }

    public ValidatableResponse getUserOrderList(String accessToken) {
        return given()
                .spec(getSpec())
                .header("Authorization", accessToken)
                .when()
                .get(USER_ORDER_LIST_PATH)
                .then();
    }

    public ValidatableResponse getIngredientList() {
        return given()
                .spec(getSpec())
                .when()
                .get(INGREDIENT_LIST_PATH)
                .then();
    }
}

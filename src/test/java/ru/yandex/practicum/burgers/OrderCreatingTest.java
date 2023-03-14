package ru.yandex.practicum.burgers;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

public class OrderCreatingTest {

    private User user;
    private UserClient userClient;
    private String accessToken;
    private Order order;
    private OrderClient orderClient;

    @Before
    public void setUp() {
        userClient = new UserClient();
        orderClient = new OrderClient();
    }

    @After
    public void cleanUp() {
        if(accessToken != null) {
            userClient.delete(accessToken);
        }
    }

    // Тест 1: создание заказа с авторизацией; создание заказа с ингредиентами;

    @Test
    @DisplayName("Check status code and message of POST /api/orders")
    @Description("Basic test for POST /api/orders with valid order with authorization")
    public void orderCanBeCreatedWithAuthorization(){
        user = UserGenerator.getValidAllFields();
        ValidatableResponse createUserResponse = userClient.create(user);
        accessToken = createUserResponse.extract().path("accessToken");

        order = OrderGenerator.getValidOrderWithIngredients();
        ValidatableResponse createOrderResponse = orderClient.create(accessToken, order);

        int statusCode = createOrderResponse.extract().statusCode();
        assertEquals("Код ответа не соответствует:", SC_OK, statusCode);

        boolean result = createOrderResponse.extract().path("success");
        assertTrue("Результат не соответствует:", result);

        String burgerName = createOrderResponse.extract().path("name");
        assertNotNull("В ответе нет названия бургера:", burgerName);

        int orderNum = createOrderResponse.extract().path("order.number");
        assertTrue("В ответе нет номера заказа:", orderNum != 0);
    }

    // Тест 2: создание заказа без авторизации; создание заказа с ингредиентами;

    @Test
    @DisplayName("Check status code and message of POST /api/orders")
    @Description("Basic test for POST /api/orders with valid order without authorization")
    public void orderCanBeCreatedWithoutAuthorization(){
        accessToken = "";
        order = OrderGenerator.getValidOrderWithIngredients();
        ValidatableResponse createOrderResponse = orderClient.create(accessToken, order);

        int statusCode = createOrderResponse.extract().statusCode();
        assertEquals("Код ответа не соответствует:", SC_OK, statusCode);

        boolean result = createOrderResponse.extract().path("success");
        assertTrue("Результат не соответствует:", result);

        String burgerName = createOrderResponse.extract().path("name");
        assertNotNull("В ответе нет названия бургера:", burgerName);

        int orderNum = createOrderResponse.extract().path("order.number");
        assertTrue("В ответе нет номера заказа:", orderNum != 0);
    }

    // Тест 3: создание заказа без ингредиентов;

    @Test
    @DisplayName("Check status code and message of POST /api/orders")
    @Description("Negative test for POST /api/orders with invalid order without ingredients")
    public void orderCanNotBeCreatedWithoutIngredients(){
        accessToken = "";
        order = OrderGenerator.getInvalidOrderWithoutIngredients();
        ValidatableResponse createOrderResponse = orderClient.create(accessToken, order);

        int statusCode = createOrderResponse.extract().statusCode();
        assertEquals("Код ответа не соответствует:", SC_BAD_REQUEST, statusCode);

        boolean result = createOrderResponse.extract().path("success");
        assertFalse("Результат не соответствует:", result);

        String expectedMessage = "Ingredient ids must be provided";
        String actualMessage = createOrderResponse.extract().path("message");
        assertEquals("Сообщение не соответствует:", expectedMessage, actualMessage);
    }

    // Тест 4: создание заказа с неверным хешем ингредиентов.

    @Test
    @DisplayName("Check status code and message of POST /api/orders")
    @Description("Negative test for POST /api/orders with invalid order with invalid ingredients hash")
    public void orderCanNotBeCreatedWithInvalidIngredientsHash(){
        accessToken = "";
        order = OrderGenerator.getInvalidOrderWithInvalidIngredientsHash();
        ValidatableResponse createOrderResponse = orderClient.create(accessToken, order);

        int statusCode = createOrderResponse.extract().statusCode();
        assertEquals("Код ответа не соответствует:", SC_INTERNAL_SERVER_ERROR, statusCode);
    }
}

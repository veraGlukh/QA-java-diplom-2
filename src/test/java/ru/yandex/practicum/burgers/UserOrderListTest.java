package ru.yandex.practicum.burgers;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import static org.hamcrest.CoreMatchers.notNullValue;
import static ru.yandex.practicum.burgers.UserClient.EMPTY_ACCESS_TOKEN;
import org.hamcrest.MatcherAssert;
import java.util.List;

public class UserOrderListTest {

    private User user;
    private UserClient userClient;
    private String accessToken;
    private Order order;
    private OrderClient orderClient;
    private OrderList orderList;

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

    // Тест 1: получение заказов конкретного пользователя с авторизацией;

    @Test
    @DisplayName("Check status code and message of GET /api/orders")
    @Description("Basic test for GET /api/orders with authorization")
    public void orderListCanBeProvidedWithAuthorization() {
        user = UserGenerator.getValidAllFields();
        ValidatableResponse createUserResponse = userClient.create(user);
        accessToken = createUserResponse.extract().path("accessToken");

        order = OrderGenerator.getValidOrderWithIngredients();
        orderClient.create(accessToken, order);

        ValidatableResponse getUserOrderListResponse = orderClient.getUserOrderList(accessToken);

        int statusCode = getUserOrderListResponse.extract().statusCode();
        assertEquals("Код ответа не соответствует:", SC_OK, statusCode);

        orderList = getUserOrderListResponse.extract().body().as(OrderList.class);
        MatcherAssert.assertThat(orderList, notNullValue());

        boolean result = orderList.isSuccess();
        assertTrue("Результат не соответствует:", result);

        int expectedTotal = 1;
        int actualTotal = orderList.getTotal();
        assertEquals("Общее количество заказов пользователя не соответствует:", expectedTotal, actualTotal);

        int expectedTotalToday = 1;
        int actualTotalToday = orderList.getTotal();
        assertEquals("Количество заказов пользователя за сегодня не соответствует:", expectedTotalToday, actualTotalToday);

        String id  = orderList.getOrders().get(0).get_id();
        assertNotNull("В теле ответа нет id заказа:", id);

        List<String> ingredients = orderList.getOrders().get(0).getIngredients();
        assertFalse("В теле ответа список ингредиентов пуст:", ingredients.isEmpty());

        String status = orderList.getOrders().get(0).getStatus();
        assertNotNull("В теле ответа нет статуса заказа:", status);

        String name = orderList.getOrders().get(0).getName();
        assertNotNull("В теле ответа нет статуса заказа:", name);

        String createdAt = orderList.getOrders().get(0).getCreatedAt();
        assertNotNull("В теле ответа нет статуса заказа:", createdAt);

        String updatedAt = orderList.getOrders().get(0).getUpdatedAt();
        assertNotNull("В теле ответа нет статуса заказа:", updatedAt);

        int number = orderList.getOrders().get(0).getNumber();
        assertTrue("В теле ответа нет статуса заказа:", number != 0);
    }

    // Тест 2: получение заказов конкретного пользователя без авторизации;

    @Test
    @DisplayName("Check status code and message of GET /api/orders")
    @Description("Negative test for GET /api/orders without authorization")
    public void orderCanNotBeProvidedWithoutAuthorization() {
        user = UserGenerator.getValidAllFields();
        ValidatableResponse createUserResponse = userClient.create(user);
        accessToken = createUserResponse.extract().path("accessToken");

        order = OrderGenerator.getValidOrderWithIngredients();
        orderClient.create(accessToken, order);

        ValidatableResponse getUserOrderListResponse = orderClient.getUserOrderList(EMPTY_ACCESS_TOKEN);

        int statusCode = getUserOrderListResponse.extract().statusCode();
        assertEquals("Код ответа не соответствует:", SC_UNAUTHORIZED, statusCode);

        boolean result = getUserOrderListResponse.extract().path("success");
        assertFalse("Результат не соответствует:", result);

        String expectedMessage = "You should be authorised";
        String actualMessage = getUserOrderListResponse.extract().path("message");
        assertEquals("Сообщение не соответствует:", expectedMessage, actualMessage);
    }
}

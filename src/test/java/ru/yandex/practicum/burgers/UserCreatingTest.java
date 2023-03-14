package ru.yandex.practicum.burgers;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

public class UserCreatingTest {

    private User user;
    private UserClient userClient;
    private String accessToken;
    private String refreshToken;

    @Before
    public void setUp() {
        userClient = new UserClient();
    }

    @After
    public void cleanUp() {
        if(accessToken != null) {
            userClient.delete(accessToken);
        }
    }

    // Тест 1: создать уникального пользователя;

    @Test
    @DisplayName("Check status code and message of POST /api/auth/register")
    @Description("Basic test for POST /api/auth/register with valid user with all required fields filled in")
    public void uniqueUserCanBeCreated(){
        user = UserGenerator.getValidAllFields();
        ValidatableResponse createUserResponse = userClient.create(user);

        int statusCode = createUserResponse.extract().statusCode();
        assertEquals("Код ответа не соответствует:", SC_OK, statusCode);

        boolean result = createUserResponse.extract().path("success");
        assertTrue("Результат не соответствует:", result);

        accessToken = createUserResponse.extract().path("accessToken");
        assertNotNull("В теле ответа нет accessToken:", accessToken);

        boolean isAccessTokenFormatCorrect = accessToken.startsWith("Bearer");
        assertTrue("Некорректный формат accessToken:", isAccessTokenFormatCorrect);

        refreshToken = createUserResponse.extract().path("refreshToken");
        assertNotNull("В теле ответа нет refreshToken:", refreshToken);

        String actualEmail = createUserResponse.extract().path("user.email");
        assertEquals("Email пользователя не соответствует:", user.getEmail().toLowerCase(),actualEmail);

        String actualName = createUserResponse.extract().path("user.name");
        assertEquals("Имя пользователя не соответствует:", user.getName(),actualName);
    }

    // Тест 2: создать пользователя, который уже зарегистрирован;

    @Test
    @DisplayName("Check status code and message of POST /api/auth/register")
    @Description("Negative test for POST /api/auth/register with invalid user with existent email and name")
    public void twoSameUsersCanNotBeCreated() {
        user = UserGenerator.getValidAllFields();
        userClient.create(user);
        ValidatableResponse createUserResponse = userClient.create(user);

        int statusCode = createUserResponse.extract().statusCode();
        assertEquals("Код ответа не соответствует:", SC_FORBIDDEN, statusCode);

        boolean result = createUserResponse.extract().path("success");
        assertFalse("Результат не соответствует:", result);

        String expectedMessage = "User already exists";
        String actualMessage = createUserResponse.extract().path("message");
        assertEquals("Сообщение не соответствует:", expectedMessage, actualMessage);
    }

        // Тесты 3-8: создать пользователя и не заполнить одно из обязательных полей.

    @Test
    @DisplayName("Check status code and message of POST /api/auth/register")
    @Description("Negative test for POST /api/auth/register with invalid user with null email")
    public void userCanNotBeCreatedWithEmailNull() {
        user = UserGenerator.getInvalidWithEmailNull();
        ValidatableResponse createUserResponse = userClient.create(user);

        int statusCode = createUserResponse.extract().statusCode();
        assertEquals("Код ответа не соответствует:", SC_FORBIDDEN, statusCode);

        boolean result = createUserResponse.extract().path("success");
        assertFalse("Результат не соответствует:", result);

        String expectedMessage = "Email, password and name are required fields";
        String actualMessage = createUserResponse.extract().path("message");
        assertEquals("Сообщение не соответствует:", expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Check status code and message of POST /api/auth/register")
    @Description("Negative test for POST /api/auth/register with invalid user with empty email")
    public void userCanNotBeCreatedWithEmailEmpty() {
        user = UserGenerator.getInvalidWithEmailEmpty();
        ValidatableResponse createUserResponse = userClient.create(user);

        int statusCode = createUserResponse.extract().statusCode();
        assertEquals("Код ответа не соответствует:", SC_FORBIDDEN, statusCode);

        boolean result = createUserResponse.extract().path("success");
        assertFalse("Результат не соответствует:", result);

        String expectedMessage = "Email, password and name are required fields";
        String actualMessage = createUserResponse.extract().path("message");
        assertEquals("Сообщение не соответствует:", expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Check status code and message of POST /api/auth/register")
    @Description("Negative test for POST /api/auth/register with invalid user with null password")
    public void userCanNotBeCreatedWithPasswordNull() {
        user = UserGenerator.getInvalidWithPasswordNull();
        ValidatableResponse createUserResponse = userClient.create(user);

        int statusCode = createUserResponse.extract().statusCode();
        assertEquals("Код ответа не соответствует:", SC_FORBIDDEN, statusCode);

        boolean result = createUserResponse.extract().path("success");
        assertFalse("Результат не соответствует:", result);

        String expectedMessage = "Email, password and name are required fields";
        String actualMessage = createUserResponse.extract().path("message");
        assertEquals("Сообщение не соответствует:", expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Check status code and message of POST /api/auth/register")
    @Description("Negative test for POST /api/auth/register with invalid user with empty password")
    public void userCanNotBeCreatedWithPasswordEmpty() {
        user = UserGenerator.getInvalidWithPasswordEmpty();
        ValidatableResponse createUserResponse = userClient.create(user);

        int statusCode = createUserResponse.extract().statusCode();
        assertEquals("Код ответа не соответствует:", SC_FORBIDDEN, statusCode);

        boolean result = createUserResponse.extract().path("success");
        assertFalse("Результат не соответствует:", result);

        String expectedMessage = "Email, password and name are required fields";
        String actualMessage = createUserResponse.extract().path("message");
        assertEquals("Сообщение не соответствует:", expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Check status code and message of POST /api/auth/register")
    @Description("Negative test for POST /api/auth/register with invalid user with null name")
    public void userCanNotBeCreatedWithNameNull() {
        user = UserGenerator.getInvalidWithNameNull();
        ValidatableResponse createUserResponse = userClient.create(user);

        int statusCode = createUserResponse.extract().statusCode();
        assertEquals("Код ответа не соответствует:", SC_FORBIDDEN, statusCode);

        boolean result = createUserResponse.extract().path("success");
        assertFalse("Результат не соответствует:", result);

        String expectedMessage = "Email, password and name are required fields";
        String actualMessage = createUserResponse.extract().path("message");
        assertEquals("Сообщение не соответствует:", expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Check status code and message of POST /api/auth/register")
    @Description("Negative test for POST /api/auth/register with invalid user with empty name")
    public void userCanNotBeCreatedWithNameEmpty() {
        user = UserGenerator.getInvalidWithNameEmpty();
        ValidatableResponse createUserResponse = userClient.create(user);

        int statusCode = createUserResponse.extract().statusCode();
        assertEquals("Код ответа не соответствует:", SC_FORBIDDEN, statusCode);

        boolean result = createUserResponse.extract().path("success");
        assertFalse("Результат не соответствует:", result);

        String expectedMessage = "Email, password and name are required fields";
        String actualMessage = createUserResponse.extract().path("message");
        assertEquals("Сообщение не соответствует:", expectedMessage, actualMessage);
    }
}

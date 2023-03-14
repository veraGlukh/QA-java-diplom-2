package ru.yandex.practicum.burgers;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

public class UserChangeTest {

    private User user;
    private UserClient userClient;
    private String accessToken;

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

    // Тест 1: Изменение email пользователя с авторизацией;

    @Test
    @DisplayName("Check status code and message of PATCH /api/auth/user")
    @Description("Basic test for PATCH /api/auth/user with email change with authorization")
    public void userEmailCanBeUpdatedWithAuthorization(){
        user = UserGenerator.getValidAllFields();
        ValidatableResponse createUserResponse = userClient.create(user);
        accessToken = createUserResponse.extract().path("accessToken");

        UserGenerator.getValidWithUpdatedEmail(user);
        ValidatableResponse changeUserResponse = userClient.change(accessToken, user);

        int statusCode = changeUserResponse.extract().statusCode();
        assertEquals("Код ответа не соответствует:", SC_OK, statusCode);

        boolean result = changeUserResponse.extract().path("success");
        assertTrue("Результат не соответствует:", result);

        String actualEmail = changeUserResponse.extract().path("user.email");
        assertEquals("Email пользователя не соответствует:", user.getEmail().toLowerCase(),actualEmail);

        String actualName = changeUserResponse.extract().path("user.name");
        assertEquals("Имя пользователя не соответствует:", user.getName(),actualName);
    }

    // Тест 2: Изменение пароля пользователя с авторизацией;

    @Test
    @DisplayName("Check status code and message of PATCH /api/auth/user")
    @Description("Basic test for PATCH /api/auth/user with password change with authorization")
    public void userPasswordCanBeUpdatedWithAuthorization(){
        user = UserGenerator.getValidAllFields();
        ValidatableResponse createUserResponse = userClient.create(user);
        accessToken = createUserResponse.extract().path("accessToken");

        UserGenerator.getValidWithUpdatedPassword(user);
        ValidatableResponse changeUserResponse = userClient.change(accessToken, user);

        int statusCode = changeUserResponse.extract().statusCode();
        assertEquals("Код ответа не соответствует:", SC_OK, statusCode);

        boolean result = changeUserResponse.extract().path("success");
        assertTrue("Результат не соответствует:", result);

        String actualEmail = changeUserResponse.extract().path("user.email");
        assertEquals("Email пользователя не соответствует:", user.getEmail().toLowerCase(),actualEmail);

        String actualName = changeUserResponse.extract().path("user.name");
        assertEquals("Имя пользователя не соответствует:", user.getName(),actualName);
    }

    // Тест 3: Изменение имени пользователя с авторизацией;

    @Test
    @DisplayName("Check status code and message of PATCH /api/auth/user")
    @Description("Basic test for PATCH /api/auth/user with name change with authorization")
    public void userNameCanBeUpdatedWithAuthorization(){
        user = UserGenerator.getValidAllFields();
        ValidatableResponse createUserResponse = userClient.create(user);
        accessToken = createUserResponse.extract().path("accessToken");

        UserGenerator.getValidWithUpdatedName(user);
        ValidatableResponse changeUserResponse = userClient.change(accessToken, user);

        int statusCode = changeUserResponse.extract().statusCode();
        assertEquals("Код ответа не соответствует:", SC_OK, statusCode);

        boolean result = changeUserResponse.extract().path("success");
        assertTrue("Результат не соответствует:", result);

        String actualEmail = changeUserResponse.extract().path("user.email");
        assertEquals("Email пользователя не соответствует:", user.getEmail().toLowerCase(),actualEmail);

        String actualName = changeUserResponse.extract().path("user.name");
        assertEquals("Имя пользователя не соответствует:", user.getName(),actualName);
    }

    // Тест 4: Изменение email пользователя без авторизации;

    @Test
    @DisplayName("Check status code and message of PATCH /api/auth/user")
    @Description("Negative test for PATCH /api/auth/user with email change without authorization")
    public void userEmailCanNotBeUpdatedWithoutAuthorization(){
        user = UserGenerator.getValidAllFields();
        userClient.create(user);
        accessToken = "";

        UserGenerator.getValidWithUpdatedEmail(user);
        ValidatableResponse changeUserResponse = userClient.change(accessToken, user);

        int statusCode = changeUserResponse.extract().statusCode();
        assertEquals("Код ответа не соответствует:", SC_UNAUTHORIZED, statusCode);

        boolean result = changeUserResponse.extract().path("success");
        assertFalse("Результат не соответствует:", result);

        String expectedMessage = "You should be authorised";
        String actualMessage = changeUserResponse.extract().path("message");
        assertEquals("Сообщение не соответствует:", expectedMessage, actualMessage);
    }

    // Тест 5: Изменение пароля пользователя без авторизации;

    @Test
    @DisplayName("Check status code and message of PATCH /api/auth/user")
    @Description("Negative test for PATCH /api/auth/user with password change without authorization")
    public void userPasswordCanNotBeUpdatedWithoutAuthorization(){
        user = UserGenerator.getValidAllFields();
        userClient.create(user);
        accessToken = "";

        UserGenerator.getValidWithUpdatedPassword(user);
        ValidatableResponse changeUserResponse = userClient.change(accessToken, user);

        int statusCode = changeUserResponse.extract().statusCode();
        assertEquals("Код ответа не соответствует:", SC_UNAUTHORIZED, statusCode);

        boolean result = changeUserResponse.extract().path("success");
        assertFalse("Результат не соответствует:", result);

        String expectedMessage = "You should be authorised";
        String actualMessage = changeUserResponse.extract().path("message");
        assertEquals("Сообщение не соответствует:", expectedMessage, actualMessage);
    }

    // Тест 6: Изменение имени пользователя без авторизации;

    @Test
    @DisplayName("Check status code and message of PATCH /api/auth/user")
    @Description("Negative test for PATCH /api/auth/user with name change without authorization")
    public void userNameCanNotBeUpdatedWithoutAuthorization(){
        user = UserGenerator.getValidAllFields();
        userClient.create(user);
        accessToken = "";

        UserGenerator.getValidWithUpdatedName(user);
        ValidatableResponse changeUserResponse = userClient.change(accessToken, user);

        int statusCode = changeUserResponse.extract().statusCode();
        assertEquals("Код ответа не соответствует:", SC_UNAUTHORIZED, statusCode);

        boolean result = changeUserResponse.extract().path("success");
        assertFalse("Результат не соответствует:", result);

        String expectedMessage = "You should be authorised";
        String actualMessage = changeUserResponse.extract().path("message");
        assertEquals("Сообщение не соответствует:", expectedMessage, actualMessage);
    }
}

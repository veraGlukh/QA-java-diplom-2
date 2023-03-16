package ru.yandex.practicum.burgers;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

public class UserLoginTest {

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

    // Тест 1: логин под существующим пользователем;

    @Test
    @DisplayName("Check status code and message of POST /api/auth/login")
    @Description("Basic test for POST /api/auth/login with valid user with all required fields filled in")
    public void uniqueUserCanLogin(){
        user = UserGenerator.getValidAllFields();
        ValidatableResponse createUserResponse = userClient.create(user);
        accessToken = createUserResponse.extract().path("accessToken"); // для удаления пользователя в случае некорректной работы метода

        ValidatableResponse loginUserResponse = userClient.login(UserCredentials.from(user));

        int statusCode = loginUserResponse.extract().statusCode();
        assertEquals("Код ответа не соответствует:", SC_OK, statusCode);

        boolean result = loginUserResponse.extract().path("success");
        assertTrue("Результат не соответствует:", result);

        accessToken = loginUserResponse.extract().path("accessToken"); // для удаления пользователя после выполнения теста
        assertNotNull("В теле ответа нет accessToken:", accessToken);

        boolean isAccessTokenFormatCorrect = accessToken.startsWith("Bearer");
        assertTrue("Некорректный формат accessToken:", isAccessTokenFormatCorrect);

        refreshToken = loginUserResponse.extract().path("refreshToken");
        assertNotNull("В теле ответа нет refreshToken:", refreshToken);

        String actualEmail = loginUserResponse.extract().path("user.email");
        assertEquals("Email пользователя не соответствует:", user.getEmail().toLowerCase(),actualEmail);

        String actualName = loginUserResponse.extract().path("user.name");
        assertEquals("Имя пользователя не соответствует:", user.getName(),actualName);
    }

    // Тесты 2 - 3: логин с неверным логином и паролем.

    @Test
    @DisplayName("Check status code and message of POST /api/auth/login")
    @Description("Negative test for POST /api/auth/login with user with invalid email")
    public void userWithInvalidEmailCanNotLogin(){
        user = UserGenerator.getValidAllFields();
        ValidatableResponse createUserResponse = userClient.create(user);
        accessToken = createUserResponse.extract().path("accessToken"); // для удаления пользователя после выполнения теста

        ValidatableResponse loginUserResponse = userClient.login(UserCredentials.returnCredentialsWithInvalidEmail(user));
        accessToken = loginUserResponse.extract().path("accessToken"); // для удаления пользователя в случае некорректной работы метода

        int statusCode = loginUserResponse.extract().statusCode();
        assertEquals("Код ответа не соответствует:", SC_UNAUTHORIZED, statusCode);

        boolean result = loginUserResponse.extract().path("success");
        assertFalse("Результат не соответствует:", result);

        String expectedMessage = "email or password are incorrect";
        String actualMessage = loginUserResponse.extract().path("message");
        assertEquals("Сообщение не соответствует:", expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("Check status code and message of POST /api/auth/login")
    @Description("Negative test for POST /api/auth/login with user with invalid password")
    public void userWithInvalidPasswordCanNotLogin(){
        user = UserGenerator.getValidAllFields();
        ValidatableResponse createUserResponse = userClient.create(user);
        accessToken = createUserResponse.extract().path("accessToken"); // для удаления пользователя после выполнения теста

        ValidatableResponse loginUserResponse = userClient.login(UserCredentials.returnCredentialsWithInvalidPassword(user));
        accessToken = loginUserResponse.extract().path("accessToken"); // для удаления пользователя в случае некорректной работы метода

        int statusCode = loginUserResponse.extract().statusCode();
        assertEquals("Код ответа не соответствует:", SC_UNAUTHORIZED, statusCode);

        boolean result = loginUserResponse.extract().path("success");
        assertFalse("Результат не соответствует:", result);

        String expectedMessage = "email or password are incorrect";
        String actualMessage = loginUserResponse.extract().path("message");
        assertEquals("Сообщение не соответствует:", expectedMessage, actualMessage);
    }
}

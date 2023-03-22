package ru.yandex.practicum.burgers;

import org.apache.commons.lang3.RandomStringUtils;

public class UserCredentials {

    private String email;
    private String password;

    static int length = 1;
    static boolean useLetters = true;
    static boolean useNumbers = true;
    static String randomSymbol = RandomStringUtils.random(length, useLetters, useNumbers);

    public UserCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static UserCredentials from(User user) {
        return new UserCredentials(user.getEmail(), user.getPassword());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static UserCredentials returnCredentialsWithInvalidEmail (User user) {
        return new UserCredentials(user.getEmail() + randomSymbol, user.getPassword());
    }

    public static UserCredentials returnCredentialsWithInvalidPassword (User user) {
        return new UserCredentials(user.getEmail(), user.getPassword() + randomSymbol);
    }
}

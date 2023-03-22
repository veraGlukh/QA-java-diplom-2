package ru.yandex.practicum.burgers;

import org.apache.commons.lang3.RandomStringUtils;

public class UserGenerator {

    static int length = 8;
    static boolean useLetters = true;
    static boolean useNumbers = true;

    // Метод создания валидного пользователя со всеми полями
    public static User getValidAllFields() {

        String randomEmail = RandomStringUtils.random(length, useLetters, useNumbers) + "@" +
                RandomStringUtils.random(length, useLetters, useNumbers) + "." +
                RandomStringUtils.random(length, useLetters, useNumbers);
        String randomPassword = RandomStringUtils.random(length, useLetters, useNumbers);
        String randomName = RandomStringUtils.random(length, useLetters, useNumbers);

        return new User(randomEmail, randomPassword, randomName);
    }

    // Метод создания невалидного пользователя без логина: login = null
    public static User getInvalidWithEmailNull() {

        String randomPassword = RandomStringUtils.random(length, useLetters, useNumbers);
        String randomName = RandomStringUtils.random(length, useLetters, useNumbers);

        return new User(null, randomPassword, randomName);
    }

    // Метод создания невалидного пользователя без логина: login = ""
    public static User getInvalidWithEmailEmpty() {

        String randomPassword = RandomStringUtils.random(length, useLetters, useNumbers);
        String randomName = RandomStringUtils.random(length, useLetters, useNumbers);

        return new User("", randomPassword, randomName);
    }

    // Метод создания невалидного пользователя без пароля: password = null
    public static User getInvalidWithPasswordNull() {

        String randomEmail = RandomStringUtils.random(length, useLetters, useNumbers) + "@" +
                RandomStringUtils.random(length, useLetters, useNumbers) + "." +
                RandomStringUtils.random(length, useLetters, useNumbers);
        String randomName = RandomStringUtils.random(length, useLetters, useNumbers);

        return new User(randomEmail, null, randomName);
    }

    // Метод создания невалидного пользователя без пароля: password = ""
    public static User getInvalidWithPasswordEmpty() {

        String randomEmail = RandomStringUtils.random(length, useLetters, useNumbers) + "@" +
                RandomStringUtils.random(length, useLetters, useNumbers) + "." +
                RandomStringUtils.random(length, useLetters, useNumbers);
        String randomName = RandomStringUtils.random(length, useLetters, useNumbers);

        return new User(randomEmail, "", randomName);
    }

    // Метод создания валидного пользователя без имени: name = null
    public static User getInvalidWithNameNull() {

        String randomEmail = RandomStringUtils.random(length, useLetters, useNumbers) + "@" +
                RandomStringUtils.random(length, useLetters, useNumbers) + "." +
                RandomStringUtils.random(length, useLetters, useNumbers);
        String randomPassword = RandomStringUtils.random(length, useLetters, useNumbers);

        return new User(randomEmail, randomPassword, null);
    }

    // Метод создания валидного пользователя без имени: name = ""
    public static User getInvalidWithNameEmpty() {

        String randomEmail = RandomStringUtils.random(length, useLetters, useNumbers) + "@" +
                RandomStringUtils.random(length, useLetters, useNumbers) + "." +
                RandomStringUtils.random(length, useLetters, useNumbers);
        String randomPassword = RandomStringUtils.random(length, useLetters, useNumbers);

        return new User(randomEmail, randomPassword, "");
    }

    // Метод изменения email пользователя:
    public static void getValidWithUpdatedEmail(User user) {
        String randomUpdatedEmail = RandomStringUtils.random(length, useLetters, useNumbers) + "@" +
                RandomStringUtils.random(length, useLetters, useNumbers) + "." +
                RandomStringUtils.random(length, useLetters, useNumbers);
        user.setEmail(randomUpdatedEmail);
    }

    // Метод изменения пароля пользователя:
    public static void getValidWithUpdatedPassword(User user) {
        String randomUpdatedPassword = RandomStringUtils.random(length, useLetters, useNumbers);
        user.setPassword(randomUpdatedPassword);
    }

    // Метод изменения имени пользователя:
    public static void getValidWithUpdatedName(User user) {
        String randomUpdatedName = RandomStringUtils.random(length, useLetters, useNumbers);
        user.setPassword(randomUpdatedName);
    }
}

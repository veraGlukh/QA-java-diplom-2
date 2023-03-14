package ru.yandex.practicum.burgers;

import org.apache.commons.lang3.RandomStringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrderGenerator {

    static int maxIngredientsNum = 15;
    static String[] allIngredients = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa70",
            "61c0c5a71d1f82001bdaaa71", "61c0c5a71d1f82001bdaaa72", "61c0c5a71d1f82001bdaaa6e",
            "61c0c5a71d1f82001bdaaa73", "61c0c5a71d1f82001bdaaa74", "61c0c5a71d1f82001bdaaa6c",
            "61c0c5a71d1f82001bdaaa75", "61c0c5a71d1f82001bdaaa76", "61c0c5a71d1f82001bdaaa77",
            "61c0c5a71d1f82001bdaaa78", "61c0c5a71d1f82001bdaaa79", "61c0c5a71d1f82001bdaaa7a"};
    static Random random = new Random();
    static int length = 1;
    static boolean useLetters = true;
    static boolean useNumbers = true;
    static String randomSymbol = RandomStringUtils.random(length, useLetters, useNumbers);

    // Метод создания валидного заказа с ингредиентами
    public static Order getValidOrderWithIngredients() {
        List<String> randomIngredients = new ArrayList<>();
        randomIngredients.add(allIngredients[random.nextInt(maxIngredientsNum)]);
        randomIngredients.add(allIngredients[random.nextInt(maxIngredientsNum)]);
        return new Order(randomIngredients);
    }

    // Метод создания невалидного заказа с ингредиентами без ингредиентов
    public static Order getInvalidOrderWithoutIngredients() {
        List<String> randomIngredients = new ArrayList<>();
        return new Order(randomIngredients);
    }

    // Метод создания невалидного заказа с неверным хешем ингредиентов
    public static Order getInvalidOrderWithInvalidIngredientsHash() {
        List<String> randomIngredients = new ArrayList<>();
        randomIngredients.add(allIngredients[random.nextInt(maxIngredientsNum)] + randomSymbol);
        randomIngredients.add(allIngredients[random.nextInt(maxIngredientsNum)] + randomSymbol);
        return new Order(randomIngredients);
    }
}

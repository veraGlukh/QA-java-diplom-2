package ru.yandex.practicum.burgers;

import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrderGenerator {

    static Random random = new Random();
    static int length = 1;
    static boolean useLetters = true;
    static boolean useNumbers = true;
    static String randomSymbol = RandomStringUtils.random(length, useLetters, useNumbers);

    // Метод создания валидного заказа с ингредиентами
    public static Order getValidOrderWithIngredients() {
        OrderClient orderClient = new OrderClient();
        ValidatableResponse getIngredientListResponse = orderClient.getIngredientList();
        IngredientList ingredientList = getIngredientListResponse.extract().body().as(IngredientList.class);

        List<String> randomIngredients = new ArrayList<>();
        int randomFirstIngredientIndex = random.nextInt(ingredientList.getData().size());
        randomIngredients.add(ingredientList.getData().get(randomFirstIngredientIndex).get_id());
        int randomSecondIngredientIndex = random.nextInt(ingredientList.getData().size());
        randomIngredients.add(ingredientList.getData().get(randomSecondIngredientIndex).get_id());

        return new Order(randomIngredients);
    }

    // Метод создания невалидного заказа с ингредиентами без ингредиентов
    public static Order getInvalidOrderWithoutIngredients() {
        List<String> randomIngredients = new ArrayList<>();
        return new Order(randomIngredients);
    }

    // Метод создания невалидного заказа с неверным хешем ингредиентов
    public static Order getInvalidOrderWithInvalidIngredientsHash() {
        OrderClient orderClient = new OrderClient();
        ValidatableResponse getIngredientListResponse = orderClient.getIngredientList();
        IngredientList ingredientList = getIngredientListResponse.extract().body().as(IngredientList.class);

        List<String> randomIngredients = new ArrayList<>();
        int randomFirstIngredientIndex = random.nextInt(ingredientList.getData().size());
        randomIngredients.add(ingredientList.getData().get(randomFirstIngredientIndex).get_id() + randomSymbol);
        int randomSecondIngredientIndex = random.nextInt(ingredientList.getData().size());
        randomIngredients.add(ingredientList.getData().get(randomSecondIngredientIndex).get_id() + randomSymbol);

        return new Order(randomIngredients);
    }
}

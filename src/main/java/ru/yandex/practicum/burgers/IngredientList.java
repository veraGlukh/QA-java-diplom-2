package ru.yandex.practicum.burgers;

import java.util.List;

public class IngredientList {

    private boolean success;
    private List<Ingredient> data;

    public IngredientList() {};

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Ingredient> getData() {
        return data;
    }

    public void setData(List<Ingredient> data) {
        this.data = data;
    }
}

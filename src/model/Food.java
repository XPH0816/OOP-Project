package model;

import com.google.gson.Gson;

import io.github.cdimascio.dotenv.Dotenv;
import util.FoodList;

public class Food implements java.io.Serializable{
    private String name;
    private double price;

    public Food(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }

    public String toString() {
        return String.format("%-15s (RM %8.2f)\n", this.name, this.price);
    }

    private static FoodList loadFromJson(Dotenv dotenv, Gson gson, FoodList foods) {
        String[] jsonStrs = {"FOODS_JSON", "DRINKS_JSON"};
        for (String jsonStr : jsonStrs) {
            String json = dotenv.get(jsonStr);
            foods.addAll(gson.fromJson(json, FoodList.class));
        }
        return foods;
    }

    private static FoodList loadFromEnvArray(Dotenv dotenv, Gson gson, FoodList foods) {
        String[] jsonName = {"FOODS", "DRINKS"};
        String[] jsonPrice = {"FOODS_PRICE", "DRINKS_PRICE"};

        for (int i = 0; i < jsonName.length; i++) {
            String[] names = gson.fromJson(dotenv.get(jsonName[i]), String[].class);
            Double[] prices = gson.fromJson(dotenv.get(jsonPrice[i]), Double[].class);
            for (int j = 0; j < names.length; j++) {
                foods.add(new Food(names[j], prices[j]));
            }
        }
        return foods;
    }

    public static FoodList all() {
        Dotenv dotenv = Dotenv.load();
        Gson gson = new Gson();
        FoodList foods = new FoodList();
        if(dotenv.get("LOAD_FROM_JSON").equals("true")) {
            return loadFromJson(dotenv, gson, foods);
        } else {
            return loadFromEnvArray(dotenv, gson, foods);
        }
    }
}

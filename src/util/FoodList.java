package util;

import java.util.ArrayList;
import model.Food;

public class FoodList extends ArrayList<Food> {
    @Override
    public String toString() {
        if(this.size() == 0) return String.format("No food");
        String result = "";
        for (Food food : this) {
            result += String.format("%3d %s", indexOf(food) + 1,food);
        }
        return result;
    }
}

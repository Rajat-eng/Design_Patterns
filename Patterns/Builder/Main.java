package Patterns.Builder;

import Patterns.Builder.Class.Dishes.Pasta;
import Patterns.Builder.Class.Dishes.Rice;
import Patterns.Builder.Class.Dishes.Soda;
import Patterns.Builder.Class.MealTypes.VegetarianMealBuilder;
import Patterns.Builder.Interface.MealBuilder;

public class Main {
    public static void main(String[] args) {
        MealBuilder vegetarianMealBuilder= new VegetarianMealBuilder();
        vegetarianMealBuilder.buildMainCourse(new Pasta());
        vegetarianMealBuilder.buildSideDish(new Rice());
        vegetarianMealBuilder.buildDrink(new Soda());
        vegetarianMealBuilder.getMeal().serveMeal();
    }
}

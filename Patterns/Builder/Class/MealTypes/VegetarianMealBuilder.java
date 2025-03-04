package Patterns.Builder.Class.MealTypes;

import Patterns.Builder.Class.Meal;
import Patterns.Builder.Interface.Drink;
import Patterns.Builder.Interface.MainCourse;
import Patterns.Builder.Interface.MealBuilder;
import Patterns.Builder.Interface.SideDish;

public class VegetarianMealBuilder implements MealBuilder {
    private Meal meal;
    public VegetarianMealBuilder(){
        this.meal=new Meal();
    }

    @Override
    public void buildMainCourse(MainCourse mainCourse) {
        meal.setMainCourse(mainCourse);  // Set the selected main course
    }

    @Override
    public void buildSideDish(SideDish sideDish) {
        meal.setSideDish(sideDish);  // Set the selected side dish
    }

    @Override
    public void buildDrink(Drink drink) {
        meal.setDrink(drink);  // Default drink for vegetarian meal
    }

    @Override
    public Meal getMeal() {
        return meal;
    }
}

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
    public MealBuilder buildMainCourse(MainCourse mainCourse) {
        meal.setMainCourse(mainCourse);  // Set the selected main course
        return this;
    }

    @Override
    public MealBuilder buildSideDish(SideDish sideDish) {
        meal.setSideDish(sideDish);  // Set the selected side dish
        return this;
    }

    @Override
    public MealBuilder buildDrink(Drink drink) {
        meal.setDrink(drink);  // Default drink for vegetarian meal
        return this;
    }

    @Override
    public Meal getMeal() {
        return meal;
    }
}

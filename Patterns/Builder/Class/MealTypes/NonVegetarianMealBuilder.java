package Patterns.Builder.Class.MealTypes;

import Patterns.Builder.Class.Meal;
import Patterns.Builder.Interface.Drink;
import Patterns.Builder.Interface.MainCourse;
import Patterns.Builder.Interface.MealBuilder;
import Patterns.Builder.Interface.SideDish;

public class NonVegetarianMealBuilder implements MealBuilder {
    private Meal meal;
    public NonVegetarianMealBuilder(){
        this.meal=new Meal();
    }

    @Override
    public MealBuilder buildMainCourse(MainCourse mainCourse) {
        meal.setMainCourse(mainCourse);  
        return this;
    }

    @Override
    public MealBuilder buildSideDish(SideDish sideDish) {
        meal.setSideDish(sideDish);
        return this;  
    }

    @Override
    public MealBuilder buildDrink(Drink drink) {
        meal.setDrink(drink);  
        return this;
    }

    @Override
    public Meal getMeal() {
        return meal;
    }
}

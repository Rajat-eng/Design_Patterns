package Patterns.Builder.Interface;

import Patterns.Builder.Class.Meal;

public interface MealBuilder {
    MealBuilder buildMainCourse(MainCourse mainCourse);
    MealBuilder buildSideDish(SideDish sideDish);
    MealBuilder buildDrink(Drink drink);
    Meal getMeal();
} 

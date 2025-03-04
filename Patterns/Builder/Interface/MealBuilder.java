package Patterns.Builder.Interface;

import Patterns.Builder.Class.Meal;

public interface MealBuilder {
    void buildMainCourse(MainCourse mainCourse);
    void buildSideDish(SideDish sideDish);
    void buildDrink(Drink drink);
    Meal getMeal();
} 

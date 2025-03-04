package Patterns.Builder.Class;

import Patterns.Builder.Interface.Drink;
import Patterns.Builder.Interface.MainCourse;
import Patterns.Builder.Interface.SideDish;

public class Meal {
    private MainCourse mainCourse;
    private SideDish sideDish;
    private Drink drink;

    // Setters for the meal components
    public void setMainCourse(MainCourse mainCourse) {
        this.mainCourse = mainCourse;
    }

    public void setSideDish(SideDish sideDish) {
        this.sideDish = sideDish;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }

    // Method to serve the complete meal
    public void serveMeal() {
        System.out.println("Your meal is Prepared");
        if (mainCourse != null) mainCourse.serve();
        if (sideDish != null) sideDish.serve();
        if (drink != null) drink.serve();
    }
}

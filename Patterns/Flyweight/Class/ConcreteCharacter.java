package Patterns.Flyweight.Class;
import Patterns.Flyweight.Interface.ICharacter;


public class ConcreteCharacter implements ICharacter {
    private char character;
    public ConcreteCharacter(char character) {
        this.character = character;
    }
    @Override
    public void display(String font) {
        System.out.println("Character: " + character + ", Font: " + font);
    }
}

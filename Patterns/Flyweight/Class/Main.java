package Patterns.Flyweight.Class;

public class Main {
    public static void main(String[] args) {
        CharacterFactory characterFactory = new CharacterFactory();
        Character character1 = characterFactory.getCharacter('A');
        character1.display("Arial");
        Character character2 = characterFactory.getCharacter('B');
        character2.display("Times New Roman");
        Character character3 = characterFactory.getCharacter('A');
        character3.display("Calibri");
    }
}

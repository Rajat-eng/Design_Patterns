package Patterns.Flyweight.Class;

import Patterns.Flyweight.Interface.ICharacter;

public class Main {
    public static void main(String[] args) {
        CharacterFactory characterFactory = new CharacterFactory();
        ICharacter character1 = characterFactory.getCharacter('A');
        character1.display("Arial");
        ICharacter character2 = characterFactory.getCharacter('B');
        character2.display("Times New Roman");
        ICharacter character3 = characterFactory.getCharacter('A');
        character3.display("Calibri");
    }
}

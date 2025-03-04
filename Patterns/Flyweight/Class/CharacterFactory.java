package Patterns.Flyweight.Class;

import java.util.HashMap;
import java.util.Map;

// Factory that returns Character instances
public class CharacterFactory {
    private Map<Character, Character> characterCache;

    public CharacterFactory() {
        characterCache = new HashMap<>();
    }

    public Character getCharacter(char c) {
        Character character = characterCache.get(c);
        if (character == null) {
            character = new ConcreteCharacter(c);  // ConcreteCharacter implements Character
            characterCache.put(c, character);
        }
        return character;
    }
}

package Patterns.Flyweight.Class;

import java.util.HashMap;
import java.util.Map;

import Patterns.Flyweight.Interface.ICharacter;


// Factory that returns Character instances
public class CharacterFactory {
    private Map<Character, ICharacter> characterCache;

    public CharacterFactory() {
        characterCache = new HashMap<>();
    }

    public ICharacter getCharacter(char c) {
        ICharacter character = characterCache.get(c);
        if (character == null) {
            ConcreteCharacter newcharacter = new ConcreteCharacter(c);  // Concrete Character implements Character
            characterCache.put(c, newcharacter);
        }
        return characterCache.get(c);
    }
}

package process;

import alphabet.Alphabet;

public class EncryptProcessor {
    Alphabet alphabet = new Alphabet();

    public String codingProcess(String text, int shift) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            char originalChar = text.charAt(i);
            int originalCharIndex = alphabet.getCharIndex(Character.toLowerCase(originalChar));
            int newCharIndex = (alphabet.getSize() + (originalCharIndex + shift)) % alphabet.getSize();
            char newChar = Character.isUpperCase(originalChar)
                    ? Character.toUpperCase(alphabet.getCharByIndex(newCharIndex))
                    : alphabet.getCharByIndex(newCharIndex);
            result.append(newChar);
        }
        return result.toString();
    }
}
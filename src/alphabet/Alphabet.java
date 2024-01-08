package alphabet;

public class Alphabet {
    /**
     * Запятая (,) и точка с запятой (;): Около 10-15%.
     * Точка (.) и восклицательный знак (!): Обычно менее 5%.
     * Вопросительный знак (?): Обычно менее 2-3%.
     * Двоеточие (:): Около 1-2%.
     * Тире —: Менее 1%.
     * Кавычки (", ', «»): В зависимости от стиля текста, но обычно менее 5%.
     */

    private final char[] ALPHABET = {'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з',
            'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'ю', 'я', ' ','.', ',', '\'',
            '«', '»', '"', ':', ';', '!', '-', '?','*', '(', ')'};

    public int getCharIndex(char ch) {
        int foundIndex = -1;
        for (int i = 0; i < ALPHABET.length; i++) {
            if (ch == (ALPHABET[i])) {
                foundIndex = i;
                break;
            }
        }
        if (foundIndex != -1) {
            return foundIndex;
        }
        return -1;
    }

    public char getCharByIndex(int index) {
        return ALPHABET[index];
    }

    public int getSize() {
        return ALPHABET.length;
    }


}

package org.itmo.fuzzing.lect2;

import java.util.Random;

public class StringMutator {

    private static final Random random = new Random();

    // Удаление случайного символа из строки
    public static String deleteRandomCharacter(String s) {
        if (s.isEmpty()) {
            return s;
        }
        int pos = random.nextInt(s.length());
        return s.substring(0, pos) + s.substring(pos + 1);
    }

    // Вставка случайного символа в строку
    public static String insertRandomCharacter(String s) {
        int pos = random.nextInt(s.length() + 1);
        char randomCharacter = (char) (random.nextInt(96) + 32); // Генерация случайного символа в диапазоне ASCII (32-126)
        return s.substring(0, pos) + randomCharacter + s.substring(pos);
    }

    // Изменение случайного бита в случайном символе строки
    public static String flipRandomCharacter(String s) {
        if (s.isEmpty()) {
            return s;
        }

        int pos = random.nextInt(s.length());
        char c = s.charAt(pos);
        int bit = 1 << random.nextInt(7); // Генерация случайного бита (от 0 до 6)
        char newC = (char) (c ^ bit); // Изменение бита в символе
        return s.substring(0, pos) + newC + s.substring(pos + 1);
    }

    // Пример использования
    public static void main(String[] args) {
        String input = "example";
        String result = deleteRandomCharacter(input);
        System.out.println("Original string: " + input);
        System.out.println("Modified string: " + result);
    }
}
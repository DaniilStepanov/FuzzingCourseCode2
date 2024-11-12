package org.itmo.fuzzing.task1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataParser {
    private static final String BLOCK_START = "{";
    private static final String BLOCK_END = "}";
    private static final String ARRAY_START = "[";
    private static final String ARRAY_END = "]";
    private static final String KEY_VALUE_SEPARATOR = ":";
    private static final String COMMENT_PREFIX = "#";

    public static void main(String[] args) {
        var filePath = args[0];

        Map<String, Object> data = new HashMap<>();
        try {
            data = parseFile(filePath);
            System.out.println("I am okay");
            System.exit(0);
        } catch (IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("I am failed but everything fine");
        }

    }

    private static Map<String, Object> parseFile(String filePath) throws IOException {
        Map<String, Object> result = new HashMap<>();
        StringBuilder currentBlock = new StringBuilder();
        Stack<String> currentKeys = new Stack<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Игнорируем комментарии
                if (line.startsWith(COMMENT_PREFIX)) {
                    continue;
                }

                // Проверяем на начало блока
                if (line.endsWith(BLOCK_START)) {
                    var currentKey = line.substring(0, line.indexOf(BLOCK_START)).trim();
                    currentKeys.add(currentKey);
                    currentBlock.append(line);
                    continue;
                }

                // Проверяем на конец блока
                if (line.endsWith(BLOCK_END)) {
                    var currentKey = currentKeys.pop();
                    currentBlock.append("\n").append(line);
                    result.put(currentKey, parseBlock(currentBlock.toString()));
                    currentBlock.setLength(0); // Очищаем текущий блок
                    continue;
                }

                // Обработка ключ-значение
                if (line.contains(KEY_VALUE_SEPARATOR)) {
                    String[] keyValue = line.split(KEY_VALUE_SEPARATOR, 2);
                    String key = keyValue[0].trim();
                    String value = keyValue[1].trim();
                    var parsedValue = parseValue(value);
                    if (parsedValue instanceof List<?> l && l.size() == 5) {
                        if (l.get(0) instanceof String s && s.contains("a")) {
                            if (l.get(1) instanceof String s1 && s1.contains("b")) {
                                if (l.get(2) instanceof String s2 && s2.contains("c")) {
                                    throw new IllegalStateException("You have found a bug");
                                }
                            }
                        }
                    }
                    result.put(key, parseValue(value));
                }
            }
        }
        return result;
    }

    private static Object parseBlock(String block) {
        Map<String, Object> innerBlock = new HashMap<>();
        String[] lines = block.split("\n");
        String currentKey = null;

        for (String line : lines) {
            line = line.trim();

            if (line.startsWith(BLOCK_START) || line.startsWith(BLOCK_END)) {
                continue; // Игнорируем границы блока
            }

            if (line.contains(KEY_VALUE_SEPARATOR)) {
                String[] keyValue = line.split(KEY_VALUE_SEPARATOR, 2);
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();
                innerBlock.put(key, parseValue(value));
            }
        }

        return innerBlock;
    }

    private static Object parseValue(String value) {
        // Проверка на массив
        if (value.startsWith(ARRAY_START) && value.endsWith(ARRAY_END)) {
            return parseArray(value);
        }

        // Проверка на логическое значение
        if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
            return Boolean.parseBoolean(value);
        }

        // Проверка на число
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            // Пробуем интерпретировать как строку
            return value.replaceAll("^\"|\"$", ""); // Убираем кавычки
        }
    }

    private static List<Object> parseArray(String array) {
        List<Object> list = new ArrayList<>();
        String contents = array.substring(1, array.length() - 1).trim(); // Убираем []
        String[] items = contents.split(",");

        for (String item : items) {
            list.add(parseValue(item.trim()));
        }

        return list;
    }
}

package org.itmo.fuzzing.lect2.instrumentation;

import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

public class CoverageTracker {

    public static final ConcurrentHashMap<String, TreeSet<String>> coverage = new ConcurrentHashMap<String, TreeSet<String>>();
    public static final ConcurrentHashMap<String, TreeSet<String>> fullCoverage = new ConcurrentHashMap<String, TreeSet<String>>();



    public static void logCoverage(String methodSignature, String lineNumber) {
        if (coverage.containsKey(methodSignature)) {
            coverage.get(methodSignature).add(lineNumber);
        } else {
            coverage.put(methodSignature, new TreeSet<>());
            coverage.get(methodSignature).add(lineNumber);
        }
    }

    public static void logFullCoverage(String methodSignature, String lineNumber) {
        if (coverage.containsKey(methodSignature)) {
            coverage.get(methodSignature).add(lineNumber);
        } else {
            coverage.put(methodSignature, new TreeSet<>());
            coverage.get(methodSignature).add(lineNumber);
        }
    }

    public static void printCoverage() {
        for (Map.Entry<String, TreeSet<String>> entry : coverage.entrySet()) {
            String key = entry.getKey();
            TreeSet<String> valueList = entry.getValue();
            System.out.println("Method: " + key);
            System.out.println("Lines: ");
            valueList.forEach(System.out::println);
        }
    }

    public static ConcurrentHashMap<String, TreeSet<String>> findDifference(
            ConcurrentHashMap<String, TreeSet<String>> coverage,
            ConcurrentHashMap<String, TreeSet<String>> fullCoverage) {

        ConcurrentHashMap<String, TreeSet<String>> difference = new ConcurrentHashMap<>();

        for (Map.Entry<String, TreeSet<String>> entry : fullCoverage.entrySet()) {
            String key = entry.getKey();
            TreeSet<String> fullSet = entry.getValue();
            TreeSet<String> coverageSet = coverage.get(key);

            if (coverageSet == null) {
                // Этот ключ отсутствует в coverage, добавляем весь set
                difference.put(key, new TreeSet<>(fullSet));
            } else {
                // Находим элементы, которые есть в fullSet, но отсутствуют в coverageSet
                TreeSet<String> diff = new TreeSet<>(fullSet);
                diff.removeAll(coverageSet);
                if (!diff.isEmpty()) {
                    difference.put(key, diff);
                }
            }
        }

        for (Map.Entry<String, TreeSet<String>> entry : difference.entrySet()) {
            String key = entry.getKey();
            TreeSet<String> valueList = entry.getValue();
            System.out.println("Method: " + key);
            System.out.println("Lines: ");
            valueList.forEach(System.out::println);
        }

        return difference;
    }
}

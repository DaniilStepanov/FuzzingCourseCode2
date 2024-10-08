package org.itmo.fuzzing.lect2;

import org.itmo.fuzzing.util.SetUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class MutationCoverageFuzzer extends MutationFuzzer {
    private Set<String> coveragesSeen = new HashSet<>();
    private List<String> population;

    /**
     * Инициализация.
     *
     * @param seed - список строк для мутации.
     * @param minMutations - минимальное количество мутаций.
     * @param maxMutations - максимальное количество мутаций.
     */
    public MutationCoverageFuzzer(List<String> seed, int minMutations, int maxMutations) {
        super(seed, minMutations, maxMutations);
        coveragesSeen = new HashSet<>();
        population = new ArrayList<>();
        reset();
    }

    @Override
    public void reset() {
        super.reset();
        coveragesSeen.clear();
        population = new ArrayList<>();
    }

    /**
     * Выполняет функцию и отслеживает покрытие.
     * Если обнаружено новое покрытие, добавляет входные данные в популяцию
     * и новое покрытие в `coveragesSeen`.
     *
     * @param runner - объект для выполнения функции и отслеживания покрытия.
     * @return результат выполнения функции.
     */
    public Object run(FunctionRunner runner, String input) {
        FunctionRunner.Tuple<Object, String> resultOutcome = runner.run(input);
        Object result = resultOutcome.first;
        Set<String> diff = new HashSet<>();
        for (String el : runner.fullCoverage) {
            if (!runner.coverage.contains(el)) {
                diff.add(el);
            }
        }

        if (!SetUtils.diff(runner.coverage, coveragesSeen).isEmpty()) {
            System.out.println("NEW COVERAGE");
            // Обнаружено новое покрытие
            population.add(input);
            coveragesSeen.addAll(runner.coverage);
            System.out.println("NOT COVERED YET" + SetUtils.diff(runner.fullCoverage, coveragesSeen).stream().filter(s -> s.contains("httpProgram")).collect(Collectors.toSet()));
            System.out.println();
        }

        return result;
    }

    public void fuzz(FunctionRunner runner) {
        for (int i = 0; i < 100_000_000; i++) {
            String input = fuzz();
            run(runner, input);
        }
    }

    // Пример использования
    public static void main(String[] args) {
        // Пример создания экземпляра и использования MutationCoverageFuzzer
    }
}
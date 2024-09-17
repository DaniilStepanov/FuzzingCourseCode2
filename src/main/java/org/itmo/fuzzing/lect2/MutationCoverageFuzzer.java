package org.itmo.fuzzing.lect2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class MutationCoverageFuzzer extends MutationFuzzer {
    private final Set<Set<String>> coveragesSeen = new HashSet<>();
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
        String outcome = resultOutcome.second;

//        Set<String> newCoverage = new HashSet<>(runner.coverage);
//
//        if (outcome.equals("PASS") && !coveragesSeen.contains(newCoverage)) {
//            // Обнаружено новое покрытие
//            population.add(inp);
//            coveragesSeen.add(newCoverage);
//        }

        return result;
    }

    // Пример использования
    public static void main(String[] args) {
        // Пример создания экземпляра и использования MutationCoverageFuzzer
    }
}
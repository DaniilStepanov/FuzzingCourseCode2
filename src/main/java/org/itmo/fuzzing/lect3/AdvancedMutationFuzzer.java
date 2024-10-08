package org.itmo.fuzzing.lect3;

import org.itmo.fuzzing.lect2.FunctionRunner;
import org.itmo.fuzzing.lect2.MutationFuzzer;
import org.itmo.fuzzing.lect2.StringMutator;
import org.itmo.fuzzing.util.SetUtils;

import java.util.*;
import java.util.stream.Collectors;

public class AdvancedMutationFuzzer extends MutationFuzzer {
    public List<String> seeds;
    public StringMutator mutator;
    public PowerSchedule schedule;
    public List<String> inputs;
    public List<Seed> population;
    private int seedIndex;
    private Random random;
    public Set<String> coveragesSeen = new HashSet<>();


    /**
     * Constructor.
     *
     * @param seeds    - a list of (input) strings to mutate.
     * @param mutator  - the mutator to apply.
     * @param schedule - the power schedule to apply.
     */
    public AdvancedMutationFuzzer(
            List<String> seeds,
            StringMutator mutator,
            PowerSchedule schedule,
            int minMutations,
            int maxMutations
    ) {
        super(seeds, minMutations, maxMutations);
        this.seeds = seeds;
        this.mutator = mutator;
        this.schedule = schedule;
        this.inputs = new ArrayList<>();
        this.random = new Random();
        reset();
    }

    /**
     * Reset the initial population and seed index
     */
    public void reset() {
        this.population = seeds.stream()
                .map(Seed::new)
                .collect(Collectors.toList());
        this.seedIndex = 0;
    }

    /**
     * Returns an input generated by fuzzing a seed in the population
     */
    public String createCandidate() {
        Seed seed = schedule.choose(population);

        // Stacking: Apply multiple mutations to generate the candidate
        String candidate = seed.getData();
        int trials = Math.min(candidate.length(), 1 << random.nextInt(5) + 1);
        for (int i = 0; i < trials; i++) {
            candidate = mutator.mutate(candidate);
        }
        return candidate;
    }

    /**
     * Returns first each seed once and then generates new inputs
     */
    @Override
    public String fuzz() {
        String inp;
        if (seedIndex < seeds.size()) {
            // Still seeding
            inp = seeds.get(seedIndex);
            seedIndex++;
        } else {
            // Mutating
            inp = createCandidate();
        }

        inputs.add(inp);
        return inp;
    }

    @Override
    public String mutate(String input) {
        return mutator.mutate(input);
    }

    public Object run(FunctionRunner runner, String input) {
        FunctionRunner.Tuple<Object, String> resultOutcome = runner.run(input);
        Object result = resultOutcome.first;
//        coveragesSeen.addAll(runner.coverage);
//        Set<String> diff = new HashSet<>();
//        for (String el : runner.fullCoverage) {
//            if (!runner.coverage.contains(el)) {
//                diff.add(el);
//            }
//        }

        if (!SetUtils.diff(runner.coverage, coveragesSeen).isEmpty()) {
            System.out.println("NEW COVERAGE");
            System.out.println("RES = " + result);
            // Обнаружено новое покрытие
            population.add(new Seed(input));
            coveragesSeen.addAll(runner.coverage);
//            System.out.println("NOT COVERED YET" + SetUtils.diff(runner.fullCoverage, coveragesSeen).stream().filter(s -> s.contains("httpProgram")).collect(Collectors.toSet()));
//            System.out.println();
            System.out.println("INPUT = " + input);
        }
        return result;
    }

    public void fuzz(FunctionRunner runner, int trials) {
        for (int i = 0; i < trials; i++) {
            String input = fuzz();
            Object res = run(runner, input);
        }
    }

    // Getters and setters
    public List<String> getInputs() {
        return inputs;
    }
}
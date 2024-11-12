package org.itmo.fuzzing.task2;

import org.itmo.fuzzing.lect2.FunctionRunner;
import org.itmo.fuzzing.lect2.instrumentation.Pair;
import org.itmo.fuzzing.lect3.AdvancedMutationFuzzer;
import org.itmo.fuzzing.lect3.CrashMe;
import org.itmo.fuzzing.lect3.PowerSchedule;
import org.itmo.fuzzing.lect9.FuzzMutator;

import java.util.Arrays;

public class Fuzzer {

    public static void main(String[] args) {
        var crashMeRunner = new FunctionRunner(s -> {
            var mySolution = Solution.simplifyPath(s);
            var canonicalSolution = CanonicalSolution.Companion.canonicalSimplifyPath(s);
            return Pair.of(mySolution, canonicalSolution);
        });
        //Usage of AdvancedMutationFuzzer
        var advancedFuzzer = new AdvancedMutationFuzzer(
                Arrays.asList("42"),
                new FuzzMutator(),
                new PowerSchedule(),
                1,
                5
        );
//        System.out.println(advancedFuzzer.fuzz());
//        System.out.println(advancedFuzzer.fuzz());
//        System.out.println(advancedFuzzer.fuzz());

        int N = 100_000_000;
        var startTime = System.currentTimeMillis();
        advancedFuzzer.fuzz(crashMeRunner, N);
        var endTime = System.currentTimeMillis();
        var coverage = advancedFuzzer.coveragesSeen;
        coverage.forEach(System.out::println);
        System.out.println("Time taken: " + (endTime - startTime));
        advancedFuzzer.population.forEach(System.out::println);
    }
}

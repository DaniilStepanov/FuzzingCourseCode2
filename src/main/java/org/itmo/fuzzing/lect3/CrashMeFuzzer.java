package org.itmo.fuzzing.lect3;

import org.itmo.fuzzing.lect2.FunctionRunner;
import org.itmo.fuzzing.lect2.StringMutator;
import org.itmo.fuzzing.lect2.URLValidator;

import java.util.Arrays;
import java.util.List;

public class CrashMeFuzzer {

    public static void main(String[] args) {
        var crashMeRunner = new FunctionRunner(s -> {
            CrashMe.crashMe(s);
            return null;
        });
//        crashMeRunner.runFunctionWithCoverage("good");
//        crashMeRunner.coverage.forEach(System.out::println);

//        List<String> seeds,
//        StringMutator mutator,
//        PowerSchedule schedule,
//        int minMutations,
//        int maxMutations

        //Usage of AdvancedMutationFuzzer
        var advancedFuzzer = new AdvancedMutationFuzzer(
                Arrays.asList("good"),
                new StringMutator(),
                new PowerSchedule(),
                1,
                5
        );
//        System.out.println(advancedFuzzer.fuzz());
//        System.out.println(advancedFuzzer.fuzz());
//        System.out.println(advancedFuzzer.fuzz());

        int N = 30_000;
        var startTime = System.currentTimeMillis();
        advancedFuzzer.fuzz(crashMeRunner, N);
        var endTime = System.currentTimeMillis();
        var coverage = advancedFuzzer.coveragesSeen;
        coverage.forEach(System.out::println);
        System.out.println("Time taken: " + (endTime - startTime));
        advancedFuzzer.population.forEach(System.out::println);


//        var greyBoxFuzzer = new GreyBoxFuzzer(
//                Arrays.asList("good"),
//                new StringMutator(),
//                new PowerSchedule(),
//                1,
//                5
//        );
//        var startTime = System.currentTimeMillis();
//        greyBoxFuzzer.fuzz(crashMeRunner, N);
//        var endTime = System.currentTimeMillis();
//        var coverage = greyBoxFuzzer.coveragesSeen;
//        coverage.forEach(System.out::println);
//        System.out.println("Time taken: " + (endTime - startTime));


//        var countingFuzzer = new CountingGreyboxFuzzer(
//                Arrays.asList("good"),
//                new StringMutator(),
//                new AFLFastSchedule(5.0),
//                1,
//                5
//        );
//        var startTime = System.currentTimeMillis();
//        countingFuzzer.fuzz(crashMeRunner, N);
//        var endTime = System.currentTimeMillis();
//        var coverage = countingFuzzer.coveragesSeen;
//        coverage.forEach(System.out::println);
//        System.out.println("Time taken: " + (endTime - startTime));
    }

}

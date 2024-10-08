package org.itmo.fuzzing.lab1;

import org.itmo.fuzzing.lect2.FunctionRunner;
import org.itmo.fuzzing.lect2.StringMutator;
import org.itmo.fuzzing.lect2.instrumentation.CoverageTracker;
import org.itmo.fuzzing.lect3.CrashMe;
import org.itmo.fuzzing.lect3.GreyBoxFuzzer;
import org.itmo.fuzzing.lect3.PowerSchedule;

import java.util.Arrays;

public class MazeFuzzer {

//    public static void main(String[] args) {
//        var mazeRunner = new FunctionRunner(MazeGenerated::maze);
//        MazeGenerated.maze("D");
//        var callGraph = CoverageTracker.callGraph;
//        var distances = CoverageTracker.calculateMethodDistances();
//        distances.entrySet().forEach(pairIntegerEntry ->
//                        System.out.println("DISTANCE FROM " + pairIntegerEntry.getKey().first + " TO " + pairIntegerEntry.getKey().second + " = " + pairIntegerEntry.getValue())
//                );
//        System.exit(0);
//        var fuzzer = new GreyBoxFuzzer(
//                Arrays.asList("D"),
//                new StringMutator(),
//                new PowerSchedule(),
//                1,
//                5
//        );
//        fuzzer.fuzz(mazeRunner, 100_000);
//    }
}

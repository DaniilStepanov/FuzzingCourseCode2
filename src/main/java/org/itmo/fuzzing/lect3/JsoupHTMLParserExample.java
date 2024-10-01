package org.itmo.fuzzing.lect3;

import org.itmo.fuzzing.lect2.FunctionRunner;
import org.itmo.fuzzing.lect2.StringMutator;
import org.jsoup.Jsoup;

import java.util.Arrays;

public class JsoupHTMLParserExample {

    public static void main(String[] args) {
        var jsoupRunner = new FunctionRunner(s -> {
            Jsoup.parse(s);
            return null;
        });
        int N = 30_000;

        var fuzzer = new GreyBoxFuzzer(
                Arrays.asList(" "),
                new StringMutator(),
                new PowerSchedule(),
                1,
                5
        );
        var startTime = System.currentTimeMillis();
        fuzzer.fuzz(jsoupRunner, N);
        var endTime = System.currentTimeMillis();
        var coverage = fuzzer.coveragesSeen;
//        coverage.forEach(System.out::println);
        System.out.println("Time taken: " + (endTime - startTime));
        System.out.println("COVERED = " + coverage.size());
    }
}

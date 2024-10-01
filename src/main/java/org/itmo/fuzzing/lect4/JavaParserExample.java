package org.itmo.fuzzing.lect4;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.printer.DefaultPrettyPrinter;
import com.github.javaparser.printer.YamlPrinter;
import com.github.javaparser.printer.configuration.DefaultPrinterConfiguration;

import java.io.File;

public class JavaParserExample {
    public static void main(String[] args) {
        try {
//            String pathToSrc = "src/main/java/org/itmo/fuzzing/lect4/Triangle.java";
            String pathToSrc = "src/main/java/org/itmo/fuzzing/lect4/GCDCalculator.java";
            File file = new File(pathToSrc);

            CompilationUnit compilationUnit = StaticJavaParser.parse(file);

            YamlPrinter printer = new YamlPrinter(true);
            System.out.println(printer.output(compilationUnit));
            System.exit(0);

//            Mutator returnMutator = new ReturnMutator(-1);
//            var methods = compilationUnit.findAll(MethodDeclaration.class);
//            var method = methods.get(0);
//            var analyzer = new MuFunctionAnalyzer(method, compilationUnit, returnMutator, compilationUnit.toString(), pathToSrc);
//            analyzer.generateMutants();
//            var mutants = analyzer.mutants;
//            for (var mutant : mutants) {
//                System.out.println(mutant.src);
//                System.out.println("------------_");
//            }
//            String oracleName ="org.itmo.fuzzing.lect4.WeakOracle";
//            double score = analyzer.executeMutants(oracleName);
//            System.out.println("Score for " + oracleName + " = " + score);

            Mutator stmtMutator = new StatementMutator(-1);
            var methods = compilationUnit.findAll(MethodDeclaration.class);
            var method = methods.get(0);
            var analyzer = new MuFunctionAnalyzer(method, compilationUnit, stmtMutator, compilationUnit.toString(), pathToSrc);
            analyzer.generateMutants();
            String oracleName = "org.itmo.fuzzing.lect4.GcdOracle";
            double score = analyzer.executeMutants(oracleName);
            System.out.println("Score for " + oracleName + " = " + score);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package org.itmo.fuzzing.lect2.instrumentation;

import org.objectweb.asm.*;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;
import java.io.*;
import java.util.concurrent.*;

public class CoverageAgent {
    public static final ConcurrentHashMap<String, String> coveredMethods = new ConcurrentHashMap<String, String>();

    public static void premain(String agentArgs, Instrumentation inst) {

        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain, byte[] classfileBuffer) {
                if (className.startsWith("org/itmo/fuzzing/lect2/") && !className.contains("instrumentation")) { // Замените на ваш пакет
                    return transformClass(className, classfileBuffer);
                }
                return null;
            }
        });

    }

    private static byte[] transformClass(String className, byte[] classfileBuffer) {
        ClassReader cr = new ClassReader(classfileBuffer);
        ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv = new ClassVisitor(Opcodes.ASM9, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
                return new MethodVisitor(Opcodes.ASM9, mv) {

                    @Override
                    public void visitLineNumber(int line, Label start) {
                        super.visitLineNumber(line, start);
                        CoverageTracker.logFullCoverage(name, Integer.toString(line));
                        String lineKey = className + ":" + line;
                        System.out.println("NAME = " + name + "LINE = " + line);
                        mv.visitLdcInsn(name);
                        mv.visitLdcInsn(lineKey);
                        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "org/itmo/fuzzing/lect2/instrumentation/CoverageTracker", "logCoverage", "(Ljava/lang/String;Ljava/lang/String;)V", false);
                    }

                };
            }
        };
        cr.accept(cv, 0);
        return cw.toByteArray();
    }
}

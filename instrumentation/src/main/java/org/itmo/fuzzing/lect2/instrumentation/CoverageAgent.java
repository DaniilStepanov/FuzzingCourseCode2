package org.itmo.fuzzing.lect2.instrumentation;

import org.objectweb.asm.*;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;

public class CoverageAgent {

    public static void premain(String agentArgs, Instrumentation inst) {

        inst.addTransformer(new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                    ProtectionDomain protectionDomain, byte[] classfileBuffer) {
                if (className.contains("jsoup") && !className.contains("instrumentation")) {
                    return asmTransformClass(className, classfileBuffer);
//                    return asmTransformClass(className, classfileBuffer);
                }
                return null;
            }
        });

    }

    private static byte[] asmTransformClass(String className, byte[] classfileBuffer) {
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
                        mv.visitLdcInsn(name);
                        mv.visitLdcInsn(Integer.toString(line));
                        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "org/itmo/fuzzing/lect2/instrumentation/CoverageTracker", "logCoverage", "(Ljava/lang/String;Ljava/lang/String;)V", false);
                    }

                };
            }
        };
        cr.accept(cv, 0);
        return cw.toByteArray();
    }
}

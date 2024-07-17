package asm001;

import org.objectweb.asm.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;

public class ModifyApp {

    public static void main(String[] args) throws IOException {
        System.out.println("Starting to modify App.class...");

        try {
            // Read the App class
            System.out.println("Reading the App class...");
            ClassReader classReader = new ClassReader("asm001/App");
            ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);

            ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM9, classWriter) {
                @Override
                public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                    MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
                    if (name.equals("getGreeting")) {
                        System.out.println("Found getGreeting method. Modifying...");
                        return new MethodVisitor(Opcodes.ASM9, mv) {
                            @Override
                            public void visitCode() {
                                super.visitCode();
                                System.out.println("Inserting new instruction in getGreeting method...");
                                mv.visitLdcInsn("Modified Hello World!");
                                mv.visitInsn(Opcodes.ARETURN);
                            }
                        };
                    }
                    return mv;
                }
            };

            classReader.accept(classVisitor, 0);
            byte[] modifiedClass = classWriter.toByteArray();

            // Define a new class with the modified bytecode
            Class<?> modifiedAppClass = MethodHandles.lookup().defineClass(modifiedClass);

            // Test the modified class
            System.out.println("Testing the modified class...");
            Object modifiedAppInstance = modifiedAppClass.getDeclaredConstructor().newInstance();
            String result = (String) modifiedAppClass.getMethod("getGreeting").invoke(modifiedAppInstance);
            System.out.println(result);

            // Write the modified class to a file
            String outputPath = "build/classes/java/main/asm001/App.class";
            System.out.println("Writing modified App.class to " + outputPath);
            try (FileOutputStream fos = new FileOutputStream(outputPath)) {
                fos.write(modifiedClass);
                System.out.println("App.class modified successfully and written to " + outputPath);
            }
        } catch (Exception e) {
            System.out.println("An error occurred while modifying the App class: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

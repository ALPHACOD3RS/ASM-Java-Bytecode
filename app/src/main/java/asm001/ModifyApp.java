package asm001;

import org.objectweb.asm.*;

// import java.io.FileOutputStream;
import java.io.IOException;
// import java.lang.invoke.MethodHandles;

public class ModifyApp {

    public static void main(String[] args) throws IOException {

        // String classPath  = "/home/alpha/Documents/projects/project-x/asm001/app/src/main/java/asm001/App.class";

        // ok now lets create a class reader to read the class byte code tho siuuuuuuucondition

        try {
            ClassReader classReader = new ClassReader("asm001/App");

            System.out.println(classReader);
    
            classReader.accept(new ClassVisitor(Opcodes.ASM9) {
                @Override
    
                public void visit(int version,int access, String name, String signature, String superName, String[] interfaces){
                    System.out.println("class : " + name);
                    System.out.println("super clas : " + superName);
    
                    if (interfaces != null && interfaces.length > 0) {
                        System.out.println("interfaces");
                        for (String i : interfaces){
    
                            System.out.println(i + " ");
                        }
                        System.out.println();
                    }
                    System.out.println("Access : " + access);
                    System.out.println("version : " + version );
                    System.out.println("signature : " + signature)
                    ;
                    
                }
    
                @Override
                public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value){
    
                    System.out.println("field " + name  + "  " + descriptor);
                    System.out.println("access " + access);
                    System.out.println("value " + value);
    
                    return super.visitField(access, name, descriptor, signature, value);
                }
    
                @Override
                public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions){
                    System.out.println("field " + name  + "  " + descriptor);
                    System.out.println("access " + access);
                    System.out.println("value " + signature);
    
                    if (exceptions != null && exceptions.length > 0) {
                        System.out.println("exeptions : ");
                        for (String e : exceptions){
                            System.out.println(e +  "  ");
                        }
                        System.out.println();
                    }
                    return visitMethod(access, name, descriptor, signature, exceptions);
    
    
            }}, 
            0);
            
        } catch (Exception e) {
            System.out.println("An error occurred while modifying the App class: " + e.getMessage());
            e.printStackTrace();
        }

       
        

      
    }
}

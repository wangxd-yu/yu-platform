package org.yu.admin.archive.util;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

public class AsposeWords_22_12 {

    public static void main(String[] args) throws Exception {

        ClassPool.getDefault().insertClassPath("E:\\桌面\\aspose-words-22.12-jdk17.jar"); //注意路径

        // 类文件1，从com.aspose.words.License类文件找到。

        CtClass clazz = ClassPool.getDefault().getCtClass("com.aspose.words.zzYvW");

        clazz.getDeclaredMethod("zzY34").setBody("{return com.aspose.words.zzZ8h.zzW8b;}");

        clazz.getDeclaredMethod("zzjS").setBody("{this.zzWbe = com.aspose.words.zzZ8h.zzW8b; zzZAO = this; }");

        CtMethod[] methodA = clazz.getDeclaredMethods();

        for (CtMethod ctMethod : methodA) {

            CtClass[] ps = ctMethod.getParameterTypes();

            if (ps.length == 1 && ctMethod.getName().equals("zzjS") && ps[0].getName().equals("java.io.InputStream")) {

                ctMethod.setBody("{this.zzWbe = com.aspose.words.zzZ8h.zzW8b; zzZAO = this; }");

            }

        }

        clazz.getDeclaredConstructors()[0].setBody("{System.out.println(\"do nothing at zzYvW.\");}");

        clazz.writeFile();


        // 类文件2，从类com.aspose.words.zzYvW的方法zzY34()进。大概是第171行。

        CtClass clazz2 = ClassPool.getDefault().getCtClass("com.aspose.words.zzWS8");

        clazz2.getDeclaredMethod("zzQG").setBody("{System.out.println(\"do nothing. this.zzW67(); this.zzW5m();\");}");

        clazz2.getDeclaredMethod("zzH0").setBody("{return com.aspose.words.zzY7H.zzZPG;}");

        //clazz2.getDeclaredConstructors()[0].setBody("{System.out.println(\"do nothing at zzWS8.\");}");

        clazz2.writeFile();


        // 类文件3（关键点：删除水印）从类com.aspose.words.zzWS8W的方法zzW5m()进，大概是665行。

        CtClass clazz3 = ClassPool.getDefault().getCtClass("com.aspose.words.zzYJa");

        clazz3.getDeclaredMethod("zzXuq").setBody("{zzYOl = 29273535023874148L;}");

        clazz3.writeFile();

    }

}
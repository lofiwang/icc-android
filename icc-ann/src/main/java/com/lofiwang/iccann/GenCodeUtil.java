package com.lofiwang.iccann;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by chunsheng.wang on 2018/9/25.
 */

public class GenCodeUtil {

    public static void createIccFile(Messager messager, Elements mElementUtils, TypeElement classElement, Filer filer) throws IOException {
        String iccConfig = "IccConfig";
        String packageName = "com.lofiwang.icc";
        TypeSpec.Builder typeSpecB = TypeSpec.classBuilder(iccConfig);
        typeSpecB.addModifiers(Modifier.PUBLIC);
        typeSpecB.addField(FieldSpec.builder(String.class, "ICC_MAIN_CLAZZ")
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                .initializer("$S", classElement.getQualifiedName().toString())
                .build());
        TypeSpec typeSpec = typeSpecB.build();
        JavaFile.builder(packageName, typeSpec).build().writeTo(filer);
    }
}

package com.lofiwang.iccann;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;

import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Created by chunsheng.wang on 2018/9/25.
 */

public class GenCodeUtil {


    public static void createIccFile(Messager messager, Elements mElementUtils, TypeElement classElement, Filer filer) throws IOException {
        HashMap<String, TypeName> fieldMap = new HashMap<>();

        for (Element encloseElement : classElement.getEnclosedElements()) {
            if (encloseElement.getKind() == ElementKind.FIELD) {
                String fieldName = encloseElement.getSimpleName().toString();
                TypeName fieldTypeName = TypeName.get(encloseElement.asType());
                fieldMap.put(fieldName, fieldTypeName);
            }
        }

        String originClazzName = classElement.getSimpleName().toString();
        String beanName;
        if (originClazzName.trim().endsWith("Meta")) {
            beanName = originClazzName.replace("Meta", "Bean");
        } else {
            beanName = originClazzName + "Bean";
        }

        PrintUtil.info(messager, "createBeanFile " + fieldMap.toString());
        PackageElement pkg = mElementUtils.getPackageOf(classElement);
        String packageName = pkg.isUnnamed() ? null : pkg.getQualifiedName().toString();
        if (packageName == null) {
            PrintUtil.error(messager, classElement.getQualifiedName() + " has no pkg name.");
            return;
        }

        TypeSpec.Builder typeSpecB = TypeSpec.classBuilder(beanName);
        typeSpecB.addModifiers(Modifier.PUBLIC);
        for (String field : fieldMap.keySet()) {
            typeSpecB.addField(fieldMap.get(field), field, Modifier.PRIVATE);
            typeSpecB.addMethod(GenCodeUtil.createSetMethod(field, fieldMap.get(field), Modifier.PUBLIC));
            typeSpecB.addMethod(GenCodeUtil.createGetMethod(field, fieldMap.get(field), Modifier.PUBLIC));
        }
        typeSpecB.addMethod(GenCodeUtil.createToStrMethod(beanName, fieldMap, Modifier.PUBLIC));
        TypeSpec typeSpec = typeSpecB.build();
        JavaFile.builder(packageName, typeSpec).build().writeTo(filer);
    }

    public static MethodSpec createSetReturnMethod(ClassName returnType, String fieldName, TypeName fieldType, Modifier... modifiers) {
        String methodName = "set" + upperFirstChar(fieldName);
        MethodSpec.Builder method = MethodSpec.methodBuilder(methodName)
                .addModifiers(modifiers)
                .returns(returnType)
                .addParameter(fieldType, fieldName)
                .addStatement("this." + fieldName + "=" + fieldName)
                .addStatement("return this");

        return method.build();
    }

    public static MethodSpec createSetMethod(String fieldName, TypeName fieldType, Modifier... modifiers) {
        String methodName = "set" + upperFirstChar(fieldName);
        MethodSpec.Builder method = MethodSpec.methodBuilder(methodName)
                .addModifiers(modifiers)
                .addParameter(fieldType, fieldName)
                .addStatement("this." + fieldName + "=" + fieldName);

        return method.build();
    }

    public static MethodSpec createGetMethod(String fieldName, TypeName fieldType, Modifier... modifiers) {
        String methodName = "get" + upperFirstChar(fieldName);
        MethodSpec.Builder method = MethodSpec.methodBuilder(methodName)
                .addModifiers(modifiers)
                .returns(fieldType)
                .addStatement("return " + fieldName);
        return method.build();
    }

    public static MethodSpec createToStrMethod(String clazzName, HashMap<String, TypeName> fieldMap, Modifier... modifiers) {
        String methodName = "toString";
        MethodSpec.Builder method = MethodSpec.methodBuilder(methodName)
                .addModifiers(modifiers)
                .addAnnotation(Override.class)
                .returns(String.class);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\"")
                .append(clazzName)
                .append("{\"");
        for (String field : fieldMap.keySet()) {
            stringBuilder.append(" + \"").append(field).append(":\" + ").append(field);
        }
        stringBuilder.append(" + \"}\"");
        method.addStatement("return " + stringBuilder.toString());
        return method.build();
    }

    public static String upperFirstChar(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }
}

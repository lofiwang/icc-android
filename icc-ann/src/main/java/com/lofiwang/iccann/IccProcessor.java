package com.lofiwang.iccann;


import com.google.auto.service.AutoService;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;


@AutoService(Processor.class)
public class IccProcessor extends AbstractProcessor {

    private Types types;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        types = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(Icc.class.getCanonicalName());
        return annotations;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (handleIcc(set, roundEnvironment)) {
//            return true;
        }
        return true;
    }

    private boolean handleIcc(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        int cardNum = 0;
        for (Element annElement : roundEnvironment.getElementsAnnotatedWith(Icc.class)) {
            TypeElement classElement = (TypeElement) annElement;

            PrintUtil.info(messager, "icc name:" + classElement.getQualifiedName() + " with @Icc");
            cardNum++;
            if (cardNum > 1) {
                PrintUtil.error(messager, "only one class can be annotated by @Icc.");
            }
            try {
                GenCodeUtil.createIccFile(messager, elementUtils, classElement, filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}

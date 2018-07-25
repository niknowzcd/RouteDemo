package com.dly.route_compiler;

import com.dly.route_annotation.Autowired;
import com.dly.route_compiler.utils.ParamTypeKinds;
import com.dly.route_compiler.utils.ParamsTypeUtils;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * Created by dly on 2018/7/23.
 */
@AutoService(Processor.class)
public class AutoWiredProcessor extends AbstractProcessor {

    private Elements elements;
    private Filer filer;
    private Messager messager;


    private Map<TypeElement, List<Element>> filedMap = new HashMap<>();     //类为key,被注解的类变量集合为value
    private Map<String, Boolean> classMap = new HashMap<>();        //注解类集合

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elements = processingEnvironment.getElementUtils();
        filer = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> autoWireds = roundEnvironment.getElementsAnnotatedWith(Autowired.class);
        try {
            collectData(autoWireds);
        } catch (IllegalAccessException e) {
            error(e.toString());
        }
        ParameterSpec parameterSpec = ParameterSpec.builder(TypeName.OBJECT, "target").build();
        if (MapUtils.isNotEmpty(filedMap)) {
            for (Map.Entry<TypeElement, List<Element>> entry : filedMap.entrySet()) {

                TypeElement targetClass = entry.getKey();     //注解所在的类
                String className = targetClass.getSimpleName() + "_AutoWired";     //生成的类名
                TypeSpec typeSpec = createTypeSpec(parameterSpec, entry);

                try {
                    if (!classMap.containsKey(className)) {
                        JavaFile.builder("com.dly.routeDemo", typeSpec).build().writeTo(filer);
                    }
                    classMap.put(className, true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
        return false;
    }


    private TypeSpec createTypeSpec(ParameterSpec parameterSpec, Map.Entry<TypeElement, List<Element>> entry) {
        MethodSpec.Builder methodSpec = MethodSpec.methodBuilder("autoWired")   //定义方法
                .addModifiers(Modifier.PUBLIC)
                .addModifiers(Modifier.STATIC)
                .addParameter(parameterSpec);

        TypeElement targetClass = entry.getKey();     //注解所在的类
        methodSpec.addStatement("$T object = ($T)target", ClassName.get(targetClass), ClassName.get(targetClass));

        List<Element> variables = entry.getValue();    //对应类中的注解变量集合
        for (Element element : variables) {
            Autowired autoWired = element.getAnnotation(Autowired.class);
            String variableName = element.getSimpleName().toString();
            String QualifiedName = "object." + variableName;
            String statement = QualifiedName + " = object." + "getIntent().";

            statement = buildStatement(QualifiedName, statement, ParamsTypeUtils.parseType(element), true);
            methodSpec.addStatement(statement, StringUtils.isEmpty(autoWired.name()) ? variableName : autoWired.name());

//            if (buildStatement(variableName, element) != null) {
//                statement += buildStatement(variableName, element);
//                methodSpec.addStatement(statement, StringUtils.isEmpty(autoWired.name()) ? variableName : autoWired.name());
//            }
        }
        String className = targetClass.getSimpleName() + "_AutoWired";     //生成的类名
        return TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(methodSpec.build())
                .build();
    }


    private String buildStatement(String originalValue, String statement, int type, boolean isActivity) {
        if (type == ParamTypeKinds.BOOLEAN.ordinal()) {
            statement += (isActivity ? ("getBooleanExtra($S, " + originalValue + ")") : ("getBoolean($S)"));
        } else if (type == ParamTypeKinds.BYTE.ordinal()) {
            statement += (isActivity ? ("getByteExtra($S, " + originalValue + "") : ("getByte($S)"));
        } else if (type == ParamTypeKinds.SHORT.ordinal()) {
            statement += (isActivity ? ("getShortExtra($S, " + originalValue + ")") : ("getShort($S)"));
        } else if (type == ParamTypeKinds.INT.ordinal()) {
            statement += (isActivity ? ("getIntExtra($S, " + originalValue + ")") : ("getInt($S)"));
        } else if (type == ParamTypeKinds.LONG.ordinal()) {
            statement += (isActivity ? ("getLongExtra($S, " + originalValue + ")") : ("getLong($S)"));
        } else if (type == ParamTypeKinds.CHAR.ordinal()) {
            statement += (isActivity ? ("getCharExtra($S, " + originalValue + ")") : ("getChar($S)"));
        } else if (type == ParamTypeKinds.FLOAT.ordinal()) {
            statement += (isActivity ? ("getFloatExtra($S, " + originalValue + ")") : ("getFloat($S)"));
        } else if (type == ParamTypeKinds.DOUBLE.ordinal()) {
            statement += (isActivity ? ("getDoubleExtra($S, " + originalValue + ")") : ("getDouble($S)"));
        } else if (type == ParamTypeKinds.STRING.ordinal()) {
            statement += (isActivity ? ("getStringExtra($S)") : ("getString($S)"));
        }
        return statement;
    }


//    private String buildStatement(String variableName, Element element) {
//        int type = ParamsTypeUtils.parseType(element);
//
//        if (type == ParamTypeKinds.BOOLEAN.ordinal()) {
//            return "getBooleanExtra($s, " + variableName + ")";
//        } else if (type == ParamTypeKinds.BYTE.ordinal()) {
//            return "getByteExtra($s, " + variableName + ")";
//        } else if (type == ParamTypeKinds.SHORT.ordinal()) {
//            return "getShortExtra($s, " + variableName + ")";
//        } else if (type == ParamTypeKinds.INT.ordinal()) {
//            return "getIntExtra($s, " + variableName + ")";
//        } else if (type == ParamTypeKinds.LONG.ordinal()) {
//            return "getLongExtra($s, " + variableName + ")";
//        } else if (type == ParamTypeKinds.CHAR.ordinal()) {
//            return "getCharExtra($s, " + variableName + ")";
//        } else if (type == ParamTypeKinds.FLOAT.ordinal()) {
//            return "getFloatExtra($s, " + variableName + ")";
//        } else if (type == ParamTypeKinds.DOUBLE.ordinal()) {
//            return "getDoubleExtra($s, " + variableName + ")";
//        } else if (type == ParamTypeKinds.STRING.ordinal()) {
//            return "getStringExtra($S)";
//        }
//        return null;
//    }


    private void collectData(Set<? extends Element> elements) throws IllegalAccessException {
        if (CollectionUtils.isEmpty(elements)) return;
        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element.getEnclosingElement();
            if (element.getModifiers().contains(Modifier.PRIVATE)) {
                throw new IllegalAccessException("The autoWired fields mustn't be decorated with private !!! please check field ["
                        + element.getSimpleName() + "] in class [" + typeElement.getQualifiedName() + "]");
            }

            if (filedMap.containsKey(typeElement)) {
                filedMap.get(typeElement).add(element);
            } else {
                List<Element> list = new ArrayList<>();
                list.add(element);
                filedMap.put(typeElement, list);
            }
        }
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(Autowired.class.getCanonicalName());
        return set;
    }

    private void error(String error) {
        messager.printMessage(Diagnostic.Kind.ERROR, error);
    }
}

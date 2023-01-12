package com.destiny.squirrel.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.thoughtworks.qdox.JavaProjectBuilder;
import com.thoughtworks.qdox.model.JavaAnnotation;
import com.thoughtworks.qdox.model.JavaClass;
import com.thoughtworks.qdox.model.JavaMethod;
import com.thoughtworks.qdox.model.JavaParameter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description qdox 解析
 * @Author destiny
 * @Date 2021-11-05 4:24 PM
 */
@Slf4j
public class QdoxAnalyze {


    private static Map<String, JavaAnnotation> formatAnnotation(List<JavaAnnotation> annotations) {
        Map<String, JavaAnnotation> result = new HashMap<>();
        if (CollUtil.isNotEmpty(annotations)) {
            for (int i = 0; i < annotations.size(); i++) {
                JavaAnnotation annotation = annotations.get(i);
                result.put(annotation.getType().getName(), annotation);
            }
        }
        return result;
    }

    public static void main(String[] args) {


        JavaProjectBuilder builder = new JavaProjectBuilder();

        String filePath = "";
        //源码目录 java文件地址
        builder.addSourceTree(new File(filePath));
        List<JavaClass> classes = (List<JavaClass>) builder.getClasses();

        JavaClass javaClass = classes.get(0);

        // 循环拿到的 java 类 全路径 + 类名称
        String defineName = javaClass.getPackageName() + "." + javaClass.getName();
        System.out.println(defineName);

        List<JavaAnnotation> annotations = javaClass.getAnnotations();
        for (JavaAnnotation annotation : annotations) {
            String annType = annotation.getType().getName();
            System.out.println(annType);
            // 获取注解内参数以及对应的值
            Map<String, Object> namedParameterMap = annotation.getNamedParameterMap();
            namedParameterMap.forEach((key, value) -> {
                System.out.println(key + " " + value);
            });
        }
        // 获取类中的方法
        List<JavaMethod> methods = javaClass.getMethods();
        // 实现的接口
        List<JavaClass> interfaces = javaClass.getInterfaces();
        // 实现类接口
        if (CollUtil.isNotEmpty(interfaces)) {
            // 获取全路径名称
            String tmp = interfaces.get(0).getFullyQualifiedName();
            System.out.println(tmp);
        }

        for (JavaMethod method : methods) {
            List<JavaAnnotation> annotations1 = method.getAnnotations();
            List<String> modifiers = method.getModifiers();
            // 方法作用域为空时，设置为 default
            if (CollUtil.isEmpty(modifiers)) {
                modifiers.add("default");
            }
            if (!(modifiers.contains("public") || modifiers.contains("default"))) {
                continue;
            }
            // 方法名称
            String name = method.getName();
            // 方法注释
            String comment = StrUtil.emptyIfNull(method.getComment());
            String uri = "";
            String shortUri = "";
            String httpMethod = "";
            String methodTag = "";
            // 方法参数
            List<JavaParameter> parameters = method.getParameters();
            for (int i = 0; i < parameters.size(); i++) {
                methodTag += ":" + parameters.get(i).getType().getValue();
            }

            String methodOveride = "0";

            Map<String, JavaAnnotation> stringJavaAnnotationMap = formatAnnotation(annotations1);
            if (stringJavaAnnotationMap.keySet().contains("Override")) {
                // 标记是重写方法
                methodOveride = "1";
            }

            for (JavaAnnotation ss : annotations1) {
                if (ss.getType().getName().endsWith("Mapping")) {
                    String tmp = null == ss.getNamedParameterMap().get("value") ?
                            ss.getNamedParameterMap().get("path").toString() :
                            ss.getNamedParameterMap().get("value").toString();
                    shortUri = tmp.replace("\"", "");
                    httpMethod = ss.getType().getName().replace("\"", "").replace("Mapping", "");
                }
            }


        }
    }
}
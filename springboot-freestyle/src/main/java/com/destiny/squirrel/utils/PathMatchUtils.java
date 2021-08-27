package com.destiny.squirrel.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-08-23 3:34 PM
 */
public class PathMatchUtils {


    public static void main(String[] args) {


        String invokeText = getPathFile("invoke_url.txt");
        String proText = getPathFile("pro_url.txt");

        List<String> regexList = new ArrayList<>();
        List<String> commonList = new ArrayList<>();

        String[] split = proText.split("\n");
        for (String node : split) {
            String nd = node.split("\t")[0] + "";
            nd = nd.trim();
            if (nd.contains("{")) {
                regexList.add(nd);
            } else {
                commonList.add(nd);
            }
        }

        String[] split1 = invokeText.split("\n");

        for (String arr : split1) {
            String uri = arr.split("\t")[2] + "";
            uri = uri.trim();
            if (commonList.contains(uri)) {
                System.out.println(arr + "\t" + uri);

            } else {
                String match = match(regexList, uri);
                System.out.println(arr + "\t" + match);
            }
        }
    }


    private static String match(List<String> regexList, String uri) {

        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (int i = 0; i < regexList.size(); i++) {
            String regex = regexList.get(i);
            boolean match = antPathMatcher.match(regex, uri);
            if (match) {
                return regex;
            }
        }
        return "";
    }


    /**
     * 获取文件的内容
     */
    private static String getPathFile(String filename) {
        ClassPathResource resource = new ClassPathResource(filename);
        return FileUtil.readString(resource.getFile(), "UTF-8");
    }


    private String extractPathFromPattern(final HttpServletRequest request) {
        String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String bestMatchPattern = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        return new AntPathMatcher().extractPathWithinPattern(bestMatchPattern, path);
    }


}

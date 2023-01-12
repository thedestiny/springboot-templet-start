package com.destiny.camel.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @Description
 * @Author destiny
 * @Date 2021-05-24 12:39 PM
 */
@Slf4j
public class LoadPropertiesUtils {

    public static void main(String[] args) {



    }


    public static Properties loadProperties(String propertyFileName) {
        InputStreamReader in = null;
        try {
            ClassLoader loder = Thread.currentThread().getContextClassLoader();

            in = new InputStreamReader(loder.getResourceAsStream(propertyFileName), "UTF-8");;
            if (in != null) {
                Properties prop = new Properties();
                prop.load(in);
                return prop;
            }
        } catch (IOException e) {
            log.error("load {} error!", propertyFileName);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("close {} error!", propertyFileName);
                }
            }
        }
        return null;
    }
}

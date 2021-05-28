package com.destiny.hiootamus.utils;

import java.math.BigDecimal;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2021-05-26 11:10 AM
 */
public class HiootamusUtils {

    public static final BigDecimal DEFAULT_PDG = BigDecimal.valueOf(0.035D);
    public static final BigDecimal DEFAULT_PDG_PERCENT = BigDecimal.valueOf(3.5D);

    public static void main(String[] args) {


        BigDecimal decimal = new BigDecimal("0.9948");
        BigDecimal decimal1 = new BigDecimal(0.98);
        System.out.println(decimal);
        System.out.println(decimal1);

        System.out.println(DEFAULT_PDG);
        System.out.println(DEFAULT_PDG_PERCENT);


        test();


    }

    private static void test(){
        try {
            int i = 1/0;
        }catch (Exception e) {

        }finally {

        }

    }



}

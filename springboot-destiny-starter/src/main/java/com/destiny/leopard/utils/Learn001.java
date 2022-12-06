package com.destiny.leopard.utils;


/**
 * @Description
 * @Author liangwenchao
 * @Date 2022-07-11 9:36 AM
 */
public class Learn001 {

    public static void main(String[] args) {

        ClassA a = new ClassA();
        a.method1();
    }


   static class ClassA{

       public ClassA() {
       }
       public void method1(){

           ClassB classB = new ClassB();
           ClassB.MqObj obj = new ClassB.MqObj();
           obj.setMqUrl("123456");
           classB.obj = obj;

           classB.method2();
        }
   }
   static class ClassB{

       public MqObj obj;

       public void method2(){
           String mqUrl = obj.getMqUrl();
           System.out.println("url " + mqUrl);

       }
       public ClassB() {
       }

       static class MqObj{
           private String mqUrl;
           public String getMqUrl() {
               return mqUrl;
           }
           public void setMqUrl(String mqUrl) {
               this.mqUrl = mqUrl;
           }
       }

   }
}

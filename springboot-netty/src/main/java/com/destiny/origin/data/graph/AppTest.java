package com.destiny.origin.data.graph;

/**
 * @Description
 * @Author liangwenchao
 * @Date 2022-12-07 4:32 PM
 */
public class AppTest {


    private GraphInterface<Town,Road> graph;
    private Town[] town;

    public void test(){

        graph = new Graph();
        town = new Town[3];

        for (int i = 0; i < 3; i++) {
            town[i] = new Town("Town_" + i);
            graph.addVertex(town[i]);
        }

        Road road1 = graph.addEdge(town[0], town[1], 2, "Road_1");

        Road road2 = graph.getEdge(town[1], town[0]);
        System.out.println(road1);
        System.out.println(road2);

        // Road road_1 = new Road(town[1], town[0], 2, "Road_1");


    }


    public static void main(String[] args) {

        AppTest test = new AppTest();
        test.test();

    }




}

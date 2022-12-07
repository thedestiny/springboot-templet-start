package com.destiny.origin.data.kruskal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainTest {

    public static void main(String[] args) {

        test2();


    }

    public static void test2() {

        System.out.println("请输入节点和边数,空格间隔");
        int nodes = 7;
        int edges = 11;
        System.out.println("节点 " + nodes + " 边数 " + edges);
        System.out.println("请输入节点间的权重");
        List<DirectedGraph.Edge> graphs = new ArrayList<>();
        DirectedGraph.Edge a = new DirectedGraph.Edge(0, 1, 7);
        DirectedGraph.Edge b = new DirectedGraph.Edge(0, 3, 5);
        DirectedGraph.Edge c = new DirectedGraph.Edge(1, 2, 8);
        DirectedGraph.Edge d = new DirectedGraph.Edge(1, 3, 9);
        DirectedGraph.Edge e = new DirectedGraph.Edge(1, 4, 7);
        DirectedGraph.Edge f = new DirectedGraph.Edge(2, 4, 5);
        DirectedGraph.Edge g = new DirectedGraph.Edge(3, 4, 15);
        DirectedGraph.Edge h = new DirectedGraph.Edge(3, 5, 6);
        DirectedGraph.Edge i = new DirectedGraph.Edge(4, 5, 8);
        DirectedGraph.Edge j = new DirectedGraph.Edge(4, 6, 9);
        DirectedGraph.Edge k = new DirectedGraph.Edge(5, 6, 11);
        graphs.add(a);
        graphs.add(b);
        graphs.add(c);
        graphs.add(d);
        graphs.add(e);
        graphs.add(f);
        graphs.add(g);
        graphs.add(h);
        graphs.add(i);
        graphs.add(j);
        graphs.add(k);


        DirectedGraph gh = new DirectedGraph(graphs);
        System.out.println("生成的图结果");
        System.out.println(gh.getDirectedGraph());
        // 计算 kruskal
        gh.kruskal(nodes);
    }

    public static void test() {

        System.out.println("请输入节点和边数,空格间隔");
        Scanner cin = new Scanner(System.in);
        int Nodes = cin.nextInt();
        int edges = cin.nextInt();
        System.out.println("节点 " + Nodes + " 边数 " + edges);
        System.out.println("请输入节点间的权重");
        DirectedGraph a = new DirectedGraph(edges);
        // 计算 kruskal
        a.kruskal(Nodes);
    }
}

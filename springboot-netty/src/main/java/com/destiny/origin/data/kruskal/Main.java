package com.destiny.origin.data.kruskal;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("请输入节点和边数,空格间隔");
        Scanner cin = new Scanner(System.in);
        int Nodes = cin.nextInt();
        int edges = cin.nextInt();
        System.out.println("节点 " +  Nodes + " 边数 " + edges);
        System.out.println("请输入节点间的权重");
        DirectedGraph a = new DirectedGraph(edges);
        // 计算 kruskal
        a.kruskal(Nodes);
    }
}

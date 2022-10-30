package com.destiny.origin.data.kruskal;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 */
public class Graph {

    protected List<String> vertex;//存放顶点
    protected int[][] edges;//存放边
    protected boolean[] isVisited;//是否被访问
    protected int numOfEdges;

    public Graph(int n) {
        this.vertex = new ArrayList<>(n);
        this.edges = new int[n][n];
        this.isVisited = new boolean[n];
    }

    //常用方法
    // 1. 获取节点个数
    protected int getNumOfVertex() {
        return vertex.size();
    }

    // 2. 打印邻接矩阵
    protected void printGraph() {
        System.out.print(" ");
        for (String s : vertex) System.out.print("  " + s);
        System.out.println();
        for (int r = 0; r < vertex.size(); r++) {
            System.out.print(vertex.get(r) + " ");
            for (int c = 0; c < vertex.size(); c++) {
                System.out.print(String.format("%2d", edges[r][c]) + " ");
            }
            System.out.println();
        }
    }

    // 3. 获取边的数目
    protected int getNumOfEdges() {
        return numOfEdges;
    }

    // 4. 获取某条边的权值
    protected int getWeightOfEdges(int v1, int v2) {
        return edges[v1][v2];
    }

    // 5. 添加节点
    protected void addVertex(String v) {
        vertex.add(v);
    }

    // 6. 添加边（双向）
    protected void addEdge(int v1, int v2, int weight) {
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
        numOfEdges++;
    }

    // 7.获取顶点索引对应的值
    protected String getValueByIndex(int i) {
        return vertex.get(i);
    }

}

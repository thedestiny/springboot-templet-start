package com.destiny.origin.data.kruskal;


import lombok.Data;

import java.util.*;

@Data
public class DirectedGraph {


    private List<Edge> directedGraph;

    public DirectedGraph(int edges) {
        this.directedGraph = new ArrayList<>();
        buildGraph(edges);
    }

    public DirectedGraph(List<Edge> graph) {
        this.directedGraph = graph;
    }

    static class Edge {
        private String endNode;
        private String startNode;
        private int weight;

        public Edge(String endNode, String startNode, int weight) {
            this.endNode = endNode;
            this.startNode = startNode;
            this.weight = weight;
        }

        public Edge(Integer endNode, Integer startNode, int weight) {
            this.endNode = String.valueOf(endNode);
            this.startNode =  String.valueOf(startNode);
            this.weight = weight;
        }

        @Override
        public String toString() {
            return this.startNode + "->" + this.endNode + ":" + this.weight;
        }
    }


    private void buildGraph(int edges) {
        Scanner cin = new Scanner(System.in);
        Edge e, e1;
        for (int i = 0; i < edges; i++) {
            String a = cin.next();
            String b = cin.next();
            int c = cin.nextInt();
            e = new Edge(a, b, c);
            e1 = new Edge(b, a, c);
            if ((directedGraph.indexOf(e) == -1) && (directedGraph.indexOf(e1) == -1)) {
                directedGraph.add(e);
            }
        }

        System.out.println("生成的图结果");
        System.out.println(directedGraph);
    }


    public void kruskal(int nodes) {

        Graph graph = new Graph(nodes);

        for (int i = 0; i < nodes; i++) {
            graph.addVertex(String.valueOf(i));
        }

        for (Edge edge : this.directedGraph) {
            graph.addEdge(Integer.valueOf(edge.startNode), Integer.valueOf(edge.endNode), edge.weight);
        }
        System.out.println("打印图");
        graph.printGraph();

        doKruskal(graph);

    }

    public static void doKruskal(Graph graph) {
        int[] ends = new int[graph.getNumOfVertex()];//用于存放顶点的终点信息
        List<String> result = new ArrayList<>();//存放每次连接的路径
        //1、把顶点都存到一个新的数组中，然后权值从小到大排序。
        // 数组元素第一个是权，后两个是两个顶点。因为无向图对称，所以只要右上部分
        int[][] edgeData = new int[graph.getNumOfEdges()][3];
        for (int i = 0, count = 0; i < graph.getNumOfVertex() && count < graph.getNumOfEdges(); i++) {
            for (int j = i + 1; j < graph.getNumOfVertex(); j++) {
                if (graph.edges[i][j] != 0) {
                    edgeData[count][0] = graph.edges[i][j];
                    edgeData[count][1] = i;
                    edgeData[count][2] = j;
                    count++;
                }
            }
        }
        Arrays.sort(edgeData, (e1, e2) -> e1[0] - e2[0]);//先按第一列元素升序排序，如果第一列相等再按第二列元素升序；
        //2、依次取出edgeData中权值较小的边，判断其两个顶点的终点，如果构不成回路就加入，否则不加
        for (int i = 0; i < edgeData.length; i++) {
            int v1 = getEnd(ends, edgeData[i][1]);
            int v2 = getEnd(ends, edgeData[i][2]);
            if (v1 != v2) {
                ends[v1] = v2;//v1的终点设为v2
                //记录哪两个顶点相连
                // result.add("" + graph.getValueByIndex(edgeData[i][1]) + " " + graph.getValueByIndex(edgeData[i][2]) + " ");
                int row = Integer.valueOf(graph.getValueByIndex(edgeData[i][1]));
                int clo = Integer.valueOf(graph.getValueByIndex(edgeData[i][2]));
                int val = graph.edges[row][clo];
                result.add("" + row + " " + clo + " " + val );
                //记录哪两个顶点相连
//                result.add("<"+ graph.getValueByIndex(edgeData[i][1])+","+graph.getValueByIndex(edgeData[i][2])+">");
            }
        }
        //3、输出最终结果
        System.out.println("各顶点间的连接线：");
        for (String e : result) System.out.println(e);
    }

    //获取某个顶点的终点，更新ends数组。这是精髓
    private static int getEnd(int[] ends, int index) {
        //如果当前顶点有终点，那就让它循环指向终点，相当于有一个指针;没有的话返回它自己
        while (ends[index] != 0) index = ends[index];
        return index;
    }


}
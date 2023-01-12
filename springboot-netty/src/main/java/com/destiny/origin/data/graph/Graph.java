package com.destiny.origin.data.graph;


import java.util.*;

/**
 * @Description
 * @Author destiny
 * @Date 2022-12-07 4:24 PM
 */
public class Graph  implements GraphInterface<Town, Road> {

    private ArrayList<LinkedList<Town>> aList;
    private Set<Town> TownSet;
    private Set<Road> RoadSet;

    public Graph(){
        aList = new ArrayList<>();
        TownSet = new HashSet<>();
        RoadSet = new HashSet<>();
    }

    @Override
    public boolean addVertex(Town v) {
        for(LinkedList<Town> list : aList){
            if(list.getFirst().getName().equals(v.getName())) {
                return false;
            }
        }
        LinkedList<Town> list = new LinkedList<>();
        list.add(v);
        aList.add(list);
        TownSet.add(v);

        return true;
    }

    @Override
    public boolean containsVertex(Town v) {
        for(LinkedList<Town> list : aList){
            if(list.getFirst().getName().equals(v.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Road addEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
        Road road = new Road(sourceVertex, destinationVertex, weight, description);
        RoadSet.add(road);

        for(int i = 0; i < aList.size(); i++) {
            if(aList.get(i).get(0).getName().equals(sourceVertex.getName())) {
                LinkedList<Town> list = aList.get(i);
                list.add(destinationVertex);
            }
        }
        return road;
    }

    @Override
    public boolean containsEdge(Town sourceVertex, Town destinationVertex) {
        for(int i = 0; i < aList.size(); i++) {
            if(aList.get(i).get(0).getName().equals(sourceVertex.getName())){
                LinkedList<Town> list = aList.get(i);
                for(Town town1 : list) {
                    if(town1.equals(destinationVertex)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Road getEdge(Town sourceVertex, Town destinationVertex) {

        Road road = null;
        Iterator<Road> iterator = RoadSet.iterator();
        while(iterator.hasNext()) {
            road = iterator.next();
            if(road.getSource().getName().equals(sourceVertex.getName())
                    && road.getDestination().getName().equals(destinationVertex.getName())) {
                return road;
            }
        }
        return road;

    }

    @Override
    public Set<Road> edgeSet() {
        return RoadSet;
    }

    @Override
    public Set<Road> edgesOf(Town vertex) {




        return null;
    }

    @Override
    public Road removeEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
        for(LinkedList<Town> list : aList){
            if(list.getFirst().getName().equals(sourceVertex.getName())) {
                if(list.contains(destinationVertex)){
                    list.remove(destinationVertex);
                }
            }
        }
        Road road = new Road(sourceVertex, destinationVertex, weight, description);
        if(RoadSet.contains(road)) {
            RoadSet.remove(road);
            return road;
        }
        return null;
    }


    @Override
    public boolean removeVertex(Town v) {
        for(LinkedList<Town> list : aList){
            if(list.getFirst().getName().equals(v.getName())) {
                aList.remove(list);
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<Town> vertexSet() {
        return TownSet;
    }

    @Override
    public ArrayList<String> shortestPath(Town sourceVertex, Town destinationVertex) {
        return null;
    }

    @Override
    public void dijkstraShortestPath(Town sourceVertex) {

    }
}

package com.destiny.origin.data.graph;

/**
 * @Description
 * @Author destiny
 * @Date 2022-12-07 4:26 PM
 */
public class Road  implements Comparable<Road> {


    private Town source;
    private Town destination;
    private int degrees;
    private String name;


    /**
     *
     * @param source One town on the road
     * @param destination Another town on the road
     * @param degrees Weight of the edge, i.e., distance from one town to the other
     * @param name Name of the road
     */
    public Road(Town source, Town destination, int degrees, String name) {
        this.source = source;
        this.destination = destination;
        this.degrees = degrees;
        this.name = name;
    }

    /**
     *
     * @param source One town on the road
     * @param destination Another town on the road
     * @param name Name of the road
     */
    public Road(Town source, Town destination, String name) {
        this.source = source;
        this.destination = destination;
        this.name = name;
        this.degrees = 0;
    }

    /**
     * This method returns true only if the edge contains the given town
     * @param town a vertex of the graph
     * @return true only if the edge is connected to the given vertex
     */
    public boolean contains(Town town) {
        return source.getName().equals(town.getName()) || destination.getName().equals(town.getName());
    }

    /**
     * This method returns the name of the road
     * @return a string
     */
    @Override
    public String toString() {
        return " " + name;
    }

    /**
     * This method returns the road name
     * @return The name of the road
     */
    public String getName() {
        return name;
    }

    /**
     * This method returns the second town on the road
     * @return A town on the road
     */
    public Town getDestination() {
        return destination;

    }

    /**
     * This method returns the first town on the road
     * @return A town on the road
     */
    public Town getSource() {
        return source;

    }

    /**
     * This method returns the distance of the road
     * @return the distance of the road
     */
    public int getWeight() {
        return degrees;

    }

    /**
     * This method Returns true if each of the ends of the road r is the same
     * as the ends of this road. Remember that a road that goes from point A
     * to point B is the same as a road that goes from point B to point A.
     * @param r road object to compare it to
     * @return true if each of the ends of the road r is the same as the ends of this road
     */
    public boolean equals(Road r) {
        boolean smatch = this.source.equals(((Road) r).source) || this.source.equals(((Road) r).destination);
        boolean dmatch = this.destination.equals(((Road) r).source) || this.destination.equals(((Road) r).destination);
        boolean result = r == this || (smatch && dmatch);

        return result;

    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * This method specified by compareTo in interface java.lang.Comparable<Road>
     * @param o an instance of Road
     * @return 0 if the road names are the same, a positive or negative number if the road names are not the same
     */
    @Override
    public int compareTo(Road o) {
        return this.getWeight() - o.getWeight();
    }


}

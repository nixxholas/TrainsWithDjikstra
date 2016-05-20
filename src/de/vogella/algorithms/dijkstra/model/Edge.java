package de.vogella.algorithms.dijkstra.model;

public class Edge {

    public final Vertex target;
    public final double weight;
    public final float xAxis;
    public final float yAxis;

    /**
     * Default Edge Constructor
     * 
     * @param argTarget
     * @param argWeight 
     */
    public Edge(Vertex argTarget, double argWeight) {
        target = argTarget;
        weight = argWeight;
        yAxis = 0;
        xAxis = 0;
    }
    
    /**
     * Edge Constructor
     * 
     * Based on Dijkstra's algorithm, the edge object has the weight
     * variable. However, this is not useful for the Train System
     * Application at all and will be deemed deprecated.
     * 
     * @param argTarget
     * @param xAxis
     * @param yAxis 
     */    
    public Edge(Vertex argTarget, float xAxis, float yAxis) {
        target = argTarget;
        this.xAxis = xAxis;
        this.yAxis = yAxis;
        weight = 0;
    }

}

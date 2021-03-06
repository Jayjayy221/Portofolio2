package com.company;

import java.util.ArrayList;
import java.util.Arrays;


public class AdjacencyGraph {
    ArrayList<Vertex> vertices;

    public AdjacencyGraph() {
        vertices = new ArrayList<Vertex>();
    }

    public void addVertex(Vertex v) {
        vertices.add(v);
    }

    public void addEdge(Vertex f, Vertex t, Integer w) {
        if (!(vertices.contains(f) && vertices.contains(t))) {
            System.out.println(" Vertex not in graph");
            return;
        }
        Edge e = new Edge(f, t, w);
    }

    public void PrintGraph() {
        for (int i = 0; i < vertices.size(); i++) {
            System.out.println(" From Vertex: " + vertices.get(i).name);
            Vertex currentfrom = vertices.get(i);
            for (int j = 0; j < currentfrom.OutEdges.size(); j++) {
                Edge currentEdge = currentfrom.OutEdges.get(j);
                System.out.println(" To: " + currentEdge.to.name + " weight: " + currentEdge.weight);
            }
            System.out.println(" ");
        }
    }

    public void MSTPrims() {

        MinHeap<Vertex> Q = new MinHeap<>();

        for (int i = 0; i < vertices.size(); i++) {
            vertices.get(i).dist = Integer.MAX_VALUE;
            vertices.get(i).prev = null;
            Q.Insert(vertices.get(i));
        }

        if (vertices.size() > 0) {
            vertices.get(0).dist = 0;
            //Q.update(S);
            int pos = Q.getPosition(vertices.get(0));
            Q.decreasekey(pos);
        }
        int MST = 0;
        while (!Q.isEmpty()) {
            Vertex u = Q.extractMin();
            for (int v = 0; v < u.OutEdges.size(); v++) {
                if (u.OutEdges.get(v).weight < u.OutEdges.get(v).to.dist) { //matrixEdge[u.index][v]==1 && matrixWeight[u.index][v]<D[v]
                    u.OutEdges.get(v).to.dist = u.OutEdges.get(v).weight;
                    u.OutEdges.get(v).to.prev = u;
                    int pos = Q.getPosition(u.OutEdges.get(v).to);
                    Q.decreasekey(pos);
                }
            }
            MST += u.dist;
        }
        System.out.println("Minimum spanning tree Distance: " + MST);
        for(int i=1; i< vertices.size();i++)
    {
        System.out.println(" parent "+ vertices.get(i).prev.name + " to " + i + " EdgeWeight: " + vertices.get(i).dist);
    }
    }
}

class Vertex implements Comparable<Vertex>{
    String name;
    ArrayList<Edge> OutEdges;
    Integer dist= Integer.MAX_VALUE;
    Vertex prev = null;
    public Vertex(String id){
        name=id;
        OutEdges=new ArrayList<Edge>();
    }
    public void addOutEdge(Edge e) {
        OutEdges.add(e);
    }

    @Override
    public int compareTo(Vertex o) {
        if (this.dist<o.dist)
            return -1;
        if (this.dist>o.dist)
            return 1;
        return 0;
    }
}

class Edge{
    Integer weight;
    Vertex from;
    Vertex to;
    public Edge(Vertex from, Vertex to, Integer cost){
        this.from=from;
        this.to=to;
        this.weight=cost;
        this.from.addOutEdge(this);
    }
}



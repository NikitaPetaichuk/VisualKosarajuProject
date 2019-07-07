package com.kosaraju.logic;

import java.util.*;


class Pair {
    private String l;
    private String r;

    public Pair(String l,String r){
        this.l = l;
        this.r = r;
    }

    public String getL(){ return l; }
    public String getR(){ return r; }
    public void setL(String l){ this.l = l; }
    public void setR(String r){ this.r = r; }

    @Override
    public boolean equals(Object obj)
    {
        if(this == obj)
            return true;
        else if(obj instanceof Pair){
            Pair other = (Pair) obj;
            if(l == other.l && r == other.r)
                return true;
        }

        return false;
    }

    @Override
    public int hashCode()
    {
        return r.hashCode() + l.hashCode();
    }
}

/*
public class Graph {
    private HashMap<Vertex, ArrayList<Vertex>> adjVertices;

    public Graph(){
        adjVertices = new HashMap<Vertex, ArrayList<Vertex>>();
    }

    public  HashMap<Vertex, ArrayList<Vertex>> getGraph()
    {
        return adjVertices;
    }

    public void addVertex(String label) {
        adjVertices.putIfAbsent(new Vertex(label), new ArrayList<Vertex>());
        System.out.println(adjVertices.values());
    }

    public void removeVertex(String label) {
        Vertex v = new Vertex(label);
        adjVertices.values().stream().forEach(e -> e.remove(v));
        adjVertices.remove(new Vertex(label));
    }

    public void addEdge(String label1, String label2) {
        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);
        adjVertices.get(v1).add(v2);
    }

    public void removeEdge(String label1, String label2) {
        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);
        List<Vertex> eV1 = adjVertices.get(v1);
        if (eV1 != null)
            eV1.remove(v2);
    }

    public List<Vertex> getAdjVertices(String label) {
        return adjVertices.get(new Vertex(label));
    }

    public Map<Vertex, List<Vertex>> transposeGraph()
    {
        List<Pair> modified = new ArrayList<Pair>();
        Map<Vertex, List<Vertex>> new_g = new HashMap<Vertex, List<Vertex>>();

        for(Vertex key : adjVertices.keySet())
        {
            for(Vertex val : adjVertices.get(key))
            {
                if(!modified.contains(new Pair(key, val)))
                {
                    new_g.putIfAbsent(new Vertex(val), new ArrayList<Vertex>());
                    new_g.get(val).add(key);
                    modified.add(new Pair(val, key));
                }
            }
        }

        return new_g;
    }
}

*/

public class Graph {

    private HashMap<String, List<String>> vertexMap;

    public Graph()
    {
        vertexMap = new HashMap<String, List<String>>();
    }

    public Graph(HashMap<String, List<String>> g)
    {
        vertexMap = g;
    }

    public  HashMap<String, List<String>> getGraph()
    {
        return vertexMap;
    }

    public void addVertex(String vertexName) {
        if (!hasVertex(vertexName)) {
            vertexMap.put(vertexName, new ArrayList<String>());
        }
    }

    public boolean hasVertex(String vertexName) {
        return vertexMap.containsKey(vertexName);
    }

    public boolean hasEdge(String vertexName1, String vertexName2) {
        if (!hasVertex(vertexName1)) return false;
        List<String> edges = vertexMap.get(vertexName1);
        return Collections.binarySearch(edges, vertexName2) != -1;
    }

    public void addEdge(String vertexName1, String vertexName2) {
        if (!hasVertex(vertexName1)) addVertex(vertexName1);
        if (!hasVertex(vertexName2)) addVertex(vertexName2);
        List<String> edges1 = vertexMap.get(vertexName1);
        edges1.add(vertexName2);
        Collections.sort(edges1);
    }

    public Graph transposeGraph()
    {
        List<Pair> modified = new ArrayList<Pair>();
        HashMap<String, List<String>> new_g = new HashMap<String, List<String>>();

        for(String key : vertexMap.keySet())
        {
            for(String val : vertexMap.get(key))
            {
                new_g.putIfAbsent(key, new ArrayList<String>());
                if(!modified.contains(new Pair(key, val)))
                {
                    new_g.putIfAbsent(val, new ArrayList<String>());
                    new_g.get(val).add(key);
                    modified.add(new Pair(val, key));
                }
            }
        }

        return new Graph(new_g);
    }

    public List<String> getNeighbours(String label) {
        return vertexMap.get(label);
    }
}
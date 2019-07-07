package VisualKosarajuLogic;

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

    public HashMap<String, List<String>> getGraph()
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
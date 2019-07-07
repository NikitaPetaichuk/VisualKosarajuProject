package com.kosaraju.logic;

import java.lang.reflect.Array;
import java.util.*;

public class KosarajuAlgorithm {
    private Graph graph = new Graph();

    public void createGraph(List<String> vertexs, List<String[]> edges)
    {
        for(String v : vertexs)
            graph.addVertex(v);
        for(String[] e : edges)
            graph.addEdge(e[0], e[1]);

        System.out.println(graph.getGraph());
    }

    public void Algorithm()
    {
        Graph t_graph = graph.transposeGraph();
        List<String> priority_list = tOutDepthTraversal(t_graph);
        System.out.println("Priority list: " + priority_list);
        List<String> c_c = depthFirstTraversal(priority_list);
        System.out.println("Strong connectivity components: " + c_c);
    }

    public List<String> tOutDepthTraversal(Graph g)
    {
        List<String> non_visited = new ArrayList<String>();
        for(String v : g.getGraph().keySet())
            non_visited.add(v);

        List<String> visited = new ArrayList<String>();
        Stack<String> stack = new Stack<String>();
        List<String> t_out_list = new ArrayList<String>();
        stack.push(g.getGraph().keySet().iterator().next());

        while (!non_visited.isEmpty()) {
            if(stack.isEmpty())
            {
                stack.push(non_visited.get(0));
                non_visited.remove(0);
            }

            String vertex = stack.peek();
            visited.add(vertex);
            non_visited.remove(vertex);
            int stack_size = stack.size();

             for(String u : g.getNeighbours(vertex))
                 if(!visited.contains(u))
                 {
                     non_visited.remove(u);
                     visited.add(u);
                     stack.push(u);
                 }

            if(stack_size == stack.size()) {
                t_out_list.add(stack.pop());
            }
        }

        Collections.reverse(t_out_list);

        return t_out_list;
    }

    public List<String> depthFirstTraversal(List<String> list_p) {
        List<String> non_visited = new ArrayList<String>();
        for (String v : graph.getGraph().keySet())
            non_visited.add(v);

        Stack<String> stack = new Stack<String>();
        List<String> c_c = new ArrayList<String>();
        stack.push(list_p.get(0));
        list_p.remove(0);

        while (!non_visited.isEmpty()) {
            if (stack.isEmpty()) {
                String first = list_p.get(0);
                stack.push(first);
                non_visited.remove(first);
                c_c.add(null);
            }

            String vertex = stack.pop();
            non_visited.remove(vertex);
            if (!c_c.contains(vertex)) {
                list_p.remove(vertex);
                c_c.add(vertex);
                for (String v : graph.getNeighbours(vertex)) {
                    stack.push(v);
                }
            }
        }
        return c_c;
    }
}

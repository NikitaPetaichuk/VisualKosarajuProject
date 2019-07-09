package VisualKosarajuLogic;

import java.util.*;

public class KosarajuAlgorithm {
    private Graph graph = new Graph();
    private List<String> transpositionStepTrace = new ArrayList<>();
    private List<String> strongConnectivityComponents;
    private List<String> originalStepTrace = new ArrayList<>();

    public void createGraph(List<String> vertexes, List<String[]> edges) {
        for(String v : vertexes)
            graph.addVertex(v);
        for(String[] e : edges)
            graph.addEdge(e[0], e[1]);
    }

    public void Algorithm() {
        Graph t_graph = graph.transposeGraph();
        List<String> priority_list = tOutDepthTraversal(t_graph);
        strongConnectivityComponents = depthFirstTraversal(priority_list);
    }

    private List<String> tOutDepthTraversal(Graph g) {
        List<String> non_visited = new ArrayList<>(g.getGraph().keySet());
        if (non_visited.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> dfsTrace = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        List<String> t_out_list = new ArrayList<>();
        String[] vertexes = g.getGraph().keySet().toArray(new String[0]);
        stack.push(vertexes[0]);

        while (!non_visited.isEmpty() || !stack.empty()) {
            if(stack.isEmpty())
            {
                dfsTrace.add("");
                stack.push(non_visited.get(0));
                non_visited.remove(0);
            }

            String vertex = stack.peek();
            dfsTrace.add(vertex);
            non_visited.remove(vertex);
            int stack_size = stack.size();

            for(String u : g.getNeighbours(vertex))
                if(!dfsTrace.contains(u))
                {
                    stack.push(u);
                    break;
                }

            if(stack_size == stack.size()) {
                t_out_list.add(stack.pop());
            }
        }

        Collections.reverse(t_out_list);
        transpositionStepTrace.addAll(dfsTrace);
        return t_out_list;
    }

    private List<String> depthFirstTraversal(List<String> list_p) {
        if (list_p.isEmpty()) {
            return new ArrayList<>();
        }

        List<String> non_visited = new ArrayList<String>(graph.getGraph().keySet());
        Stack<String> stack = new Stack<String>();
        List<String> c_c = new ArrayList<String>();
        stack.push(list_p.get(0));
        list_p.remove(0);

        while (!non_visited.isEmpty() || !stack.empty()) {
            if (stack.isEmpty()) {
                String first = list_p.get(0);
                stack.push(first);
                non_visited.remove(first);
                originalStepTrace.add("");
                c_c.add(null);
            }

            String vertex = stack.peek();
            originalStepTrace.add(vertex);
            non_visited.remove(vertex);
            if (!c_c.contains(vertex)) {
                list_p.remove(vertex);
                c_c.add(vertex);
            }

            int stackSize = stack.size();
            for (String v : graph.getNeighbours(vertex)) {
                if (non_visited.contains(v)) {
                    stack.push(v);
                    break;
                }
            }
            if (stackSize == stack.size()) {
                stack.pop();
            }
        }
        return c_c;
    }

    public List<String> getTranspositionStepTrace() {
        return transpositionStepTrace;
    }

    public List<String> getStrongConnectivityComponents() {
        return strongConnectivityComponents;
    }

    public List<String> getOriginalStepTrace() {
        return originalStepTrace;
    }
}

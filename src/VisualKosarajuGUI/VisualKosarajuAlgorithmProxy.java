package VisualKosarajuGUI;

import VisualKosarajuLogic.*;
import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;

import java.util.ArrayList;
import java.util.List;

class VisualKosarajuAlgorithmProxy extends VisualKosarajuWindow {
    private List<String> vertexes = new ArrayList<>();
    private List<String[]> edges = new ArrayList<>();
    private List<String> transpositionStepTrace;
    private List<String> strongConnectivityComponents;
    private List<String> originalStepTrace;

    VisualKosarajuAlgorithmProxy() {
        KosarajuAlgorithm mainLogic = new KosarajuAlgorithm();

        prepareGraphData();
        mainLogic.createGraph(vertexes, edges);
        mainLogic.Algorithm();
        transpositionStepTrace = mainLogic.getTranspositionStepTrace();
        strongConnectivityComponents = mainLogic.getStrongConnectivityComponents();
        originalStepTrace = mainLogic.getOriginalStepTrace();
    }

    private void prepareGraphData() {
        mxGraph graph = this.getGraph();
        graph.clearSelection();
        graph.selectAll();
        Object[] cells = graph.getSelectionCells();
        for (Object cell : cells) {
            mxCell graphElement = (mxCell) cell;
            if (graphElement.isVertex()) {
                vertexes.add(graphElement.getValue().toString());
            } else if (graphElement.isEdge()) {
                String source = graphElement.getSource().getValue().toString();
                String target = graphElement.getTarget().getValue().toString();
                edges.add(new String[]{source, target});
            }
        }
        graph.clearSelection();
    }

    String[] createAlgorithmStepTrace() {
        List<String> algorithmStepTrace = new ArrayList<>();
        algorithmStepTrace.add("");
        algorithmStepTrace.addAll(transpositionStepTrace);
        algorithmStepTrace.add("");
        algorithmStepTrace.add(null);
        algorithmStepTrace.addAll(originalStepTrace);
        algorithmStepTrace.add("");
        algorithmStepTrace.add(null);
        return algorithmStepTrace.toArray(new String[0]);
    }

    List<String> getStrongConnectivityComponents() {
        return strongConnectivityComponents;
    }
}

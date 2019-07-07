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

    VisualKosarajuAlgorithmProxy() {
        KosarajuAlgorithm mainLogic = new KosarajuAlgorithm();

        prepareGraphData();
        mainLogic.createGraph(vertexes, edges);
        mainLogic.Algorithm();
        transpositionStepTrace = mainLogic.getTranspositionStepTrace();
        strongConnectivityComponents = mainLogic.getStrongConnectivityComponents();
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
        String[] arrayOfStrongConnectivityComponents = strongConnectivityComponents.toArray(new String[0]);
        algorithmStepTrace.add("");
        algorithmStepTrace.addAll(transpositionStepTrace);
        algorithmStepTrace.add("");
        algorithmStepTrace.add(null);
        for (int i = 0; i < arrayOfStrongConnectivityComponents.length; i++) {
            if (arrayOfStrongConnectivityComponents[i] == null) {
                for (int j = i - 2; j >= 0 && arrayOfStrongConnectivityComponents[j] != null; j--) {
                    algorithmStepTrace.add(arrayOfStrongConnectivityComponents[j]);
                }
                algorithmStepTrace.add("");
            } else {
                algorithmStepTrace.add(arrayOfStrongConnectivityComponents[i]);
            }
        }
        algorithmStepTrace.add("");
        algorithmStepTrace.add(null);
        return algorithmStepTrace.toArray(new String[0]);
    }

    List<String> getStrongConnectivityComponents() {
        return strongConnectivityComponents;
    }
}

package VisualKosarajuGUI;

import javax.swing.*;

public class EdgeAdditor extends VisualKosarajuWindow {
    public EdgeAdditor() {
        Object parent = this.getGraph().getDefaultParent();
        Object vertexStart = this.getVertexHashMap().get(JOptionPane.showInputDialog("Введите вершину-начало"));
        Object vertexEnd = this.getVertexHashMap().get(JOptionPane.showInputDialog("Введите вершину-конец"));
        this.getGraph().insertEdge(parent, null, "", vertexStart, vertexEnd);
    }
}

package VisualKosarajuGUI;

import javax.swing.*;

public class VertexAdditor extends VisualKosarajuWindow {
    public VertexAdditor(String vertexName) {
        if (!vertexName.equals("")) {
            if (!this.getVertexHashMap().containsKey(vertexName)) {
                this.getGraph().getModel().beginUpdate();
                Object parent = this.getGraph().getDefaultParent();
                Object vertex = this.getGraph().insertVertex(parent, null, vertexName, 300, 30, 50, 50);
                this.getVertexHashMap().put(vertexName, vertex);
                this.getGraph().getModel().endUpdate();
            } else {
                    JOptionPane.showMessageDialog(null, "Вершина с таким именем уже существует");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Нельзя создать вершину без имени");
        }
    }
}

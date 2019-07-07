package VisualKosarajuGUI;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;

class VisualKosarajuWindow extends JFrame {
    private static mxGraph graph  = new mxGraph();
    private HashMap VertexHashMap = new HashMap();
    private String[] algorithmStepTrace;
    private List<String> strongConnectivityComponents;
    private int indexOfAlgorithmStep;
    private int indexOfTransposition;
    private boolean isEditMode;

    private String[] colors;

    private mxGraphComponent graphComponent;
    private JTextArea messageArea;

    VisualKosarajuWindow() {
        super("Visual Kosaraju");
        isEditMode = true;
        colors = new String[]{"#88fb88", "#ffff66", "#eebef1",
                              "#ffffff", "#ffa375", "#c284e0"};
        initializeGUI();
    }

    static mxGraph getGraph() {
        return graph;
    }

    private void initializeGUI() {
        setSize(new Dimension(630, 680));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT));

        createMenuBar();
        createGraphComponent();
        createStartButton();
        createNextStepButton();
        createPrevStepButton();
        createToTheEndButton();
        createToTheBeginButton();
        createResetButton();
        createMessageArea();
        createAuthorsLabel();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu openFileMenu = new JMenu("Открыть файл с графом");
        JMenu helpMenu = new JMenu("Помощь");
        JMenu authorsMenu = new JMenu("Контакты");

        menuBar.add(openFileMenu);
        menuBar.add(helpMenu);
        menuBar.add(authorsMenu);
        getContentPane().add(menuBar);
    }

    private void createGraphComponent() {
        graphComponent = new mxGraphComponent(graph);
        graphComponent.setPreferredSize(new Dimension(600,365));
        graphComponent.getGraphControl().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    if (isEditMode) {
                        Object editingGraphElement = graphComponent.getCellAt(e.getX(), e.getY());
                        if (editingGraphElement == null) {
                            addVertex(e.getX(), e.getY());
                        } else {
                            deleteVertex(editingGraphElement);
                        }
                    } else {
                            JOptionPane.showMessageDialog(null, "Нельзя модифицировать граф в данный момент");
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        getContentPane().add(graphComponent);
    }

    private void createAuthorsLabel() {
        JLabel authorsLabel = new JLabel("Создатели: Давыдов А.А., Пэтайчук Н.Г., Чемова К.А.; " +
                "Компания \"Davydov & Co.\"");
        authorsLabel.setPreferredSize(new Dimension(600,15));
        getContentPane().add(authorsLabel);
    }

    private void createStartButton() {
        JButton StartButton = new JButton("Начать визуализацию");
        StartButton.setPreferredSize(new Dimension(196, 25));
        StartButton.addActionListener((ActionEvent e) -> {
            if (isEditMode) {
                isEditMode = false;
                VisualKosarajuAlgorithmProxy algorithmProxy = new VisualKosarajuAlgorithmProxy();
                strongConnectivityComponents = algorithmProxy.getStrongConnectivityComponents();
                algorithmStepTrace = algorithmProxy.createAlgorithmStepTrace();
                for (int i = 0; i < algorithmStepTrace.length; i++) {
                    if (algorithmStepTrace[i] == null) {
                        indexOfTransposition = i;
                        break;
                    }
                }
                for (String elem : algorithmStepTrace) {
                    System.out.println(elem);
                }
                indexOfAlgorithmStep = 0;
                graphTransposition();
            }
        });
        getContentPane().add(StartButton);
    }

    private void createNextStepButton() {
        JButton NextStepButton = new JButton("Следующий шаг");
        NextStepButton.setPreferredSize(new Dimension(197, 25));
        NextStepButton.addActionListener((ActionEvent e) -> {
            if (isEditMode) {
                JOptionPane.showMessageDialog(null, "Нельзя использовать эту кнопку в Режиме Редактирования");
                return;
            } else if (indexOfAlgorithmStep == algorithmStepTrace.length - 1) {
                return;
            }
            String nowVertex = algorithmStepTrace[indexOfAlgorithmStep];
            if (nowVertex == null) {
            } else if (!nowVertex.equals("")) {
                changeColor(VertexHashMap.get(nowVertex), "#C3D9FF");
            }
            indexOfAlgorithmStep++;
            String nextVertex = algorithmStepTrace[indexOfAlgorithmStep];
            if (indexOfAlgorithmStep == algorithmStepTrace.length - 1) {
                colorConnectivityComponents();
            } else if (nextVertex == null) {
                graphTransposition();
            } else if (!nextVertex.equals("")) {
                changeColor(VertexHashMap.get(nextVertex), "#FFD9C3");
            }
        });
        getContentPane().add(NextStepButton);
    }

    private void createPrevStepButton() {
        JButton PrevStepButton = new JButton("Предыдущий шаг");
        PrevStepButton.setPreferredSize(new Dimension(196, 25));
        PrevStepButton.addActionListener((ActionEvent e) -> {
            if (isEditMode)
            {
                JOptionPane.showMessageDialog(null, "Нельзя использовать эту кнопку в Режиме Редактирования");
                return;
            } else if (indexOfAlgorithmStep == 0) {
                return;
            }
            String nowVertex = algorithmStepTrace[indexOfAlgorithmStep];
            if (indexOfAlgorithmStep == algorithmStepTrace.length - 1) {
                discolorVertexes();
            } else if (nowVertex == null) {
                graphTransposition();
            } else if (!nowVertex.equals("")) {
                changeColor(VertexHashMap.get(nowVertex), "#C3D9FF");
            }
            indexOfAlgorithmStep--;
            String prevVertex = algorithmStepTrace[indexOfAlgorithmStep];
            if (prevVertex == null) {
            } else if (!prevVertex.equals("")) {
                changeColor(VertexHashMap.get(prevVertex), "#FFD9C3");
            }
        });
        getContentPane().add(PrevStepButton);
    }

    private void createToTheEndButton() {
        JButton ToTheEndButton = new JButton("Доработать до конца");
        ToTheEndButton.setPreferredSize(new Dimension(196, 25));
        ToTheEndButton.addActionListener((ActionEvent e) -> {
            if (isEditMode)
            {
                JOptionPane.showMessageDialog(null, "Нельзя использовать эту кнопку в Режиме Редактирования");
                return;
            }
            if (indexOfAlgorithmStep < indexOfTransposition) {
                graphTransposition();
            }
            colorConnectivityComponents();
            indexOfAlgorithmStep = algorithmStepTrace.length - 1;
        });
        getContentPane().add(ToTheEndButton);
    }

    private void createToTheBeginButton() {
        JButton ToTheBeginButton = new JButton("Вернуться в начало");
        ToTheBeginButton.setPreferredSize(new Dimension(197, 25));
        ToTheBeginButton.addActionListener((ActionEvent e) -> {
            if (isEditMode)
            {
                JOptionPane.showMessageDialog(null, "Нельзя использовать эту кнопку в Режиме Редактирования");
                return;
            }
            if (indexOfAlgorithmStep >= indexOfTransposition) {
                graphTransposition();
            }
            discolorVertexes();
            indexOfAlgorithmStep = 0;
        });
        getContentPane().add(ToTheBeginButton);
    }

    private void createResetButton() {
        JButton ResetButton = new JButton("Сброс");
        ResetButton.setPreferredSize(new Dimension(196, 25));
        ResetButton.addActionListener((ActionEvent e) -> {
            if (!isEditMode) {
                isEditMode = true;
                discolorVertexes();
                if (indexOfAlgorithmStep < indexOfTransposition) {
                    graphTransposition();
                }
            }
        });
        getContentPane().add(ResetButton);
    }

    private void createMessageArea() {
        messageArea = new JTextArea();
        messageArea.setPreferredSize(new Dimension(600, 150));
        messageArea.setEnabled(false);
        messageArea.setBackground(new Color(0x000000));
        messageArea.setForeground(new Color(0xFFFFFF));
        messageArea.setFont(new Font("CourierNew", Font.PLAIN, 14));
        getContentPane().add(messageArea);
    }

    private void addVertex(int x, int y) {
        String vertexName = JOptionPane.showInputDialog("Введите имя вершины");
        if (!vertexName.equals("")) {
            if (!VertexHashMap.containsKey(vertexName)) {
                graph.getModel().beginUpdate();
                Object parent = graph.getDefaultParent();
                Object vertex = graph.insertVertex(parent, null, vertexName, x, y, 50, 50, "shape=ellipse");
                VertexHashMap.put(vertexName, vertex);
                graph.getModel().endUpdate();
            } else {
                JOptionPane.showMessageDialog(null, "Вершина с таким именем уже существует");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Нельзя создать вершину без имени");
        }
    }

    private void deleteVertex(Object vertexToDelete) {
        VertexHashMap.remove(((mxCell) vertexToDelete).getValue());
        graph.getModel().remove(vertexToDelete);
        deleteUnusedEdges();
    }

    private void changeColor(Object graphElement, String color) {
        if (graphElement != null) {
            String style = "fillColor=" + color + ";shape=ellipse";
            graph.getModel().beginUpdate();
            Object[] cellArray = new Object[1];
            cellArray[0] = graphElement;
            graph.setCellStyle(style, cellArray);
            graph.getModel().endUpdate();
        }
    }

    private void deleteUnusedEdges() {
        graph.clearSelection();
        graph.selectAll();
        Object[] cells = graph.getSelectionCells();
        for (Object cell : cells) {
            mxCell graphComponent = (mxCell) cell;
            if (graphComponent.isEdge()) {
                String source = (String) graphComponent.getSource().getValue();
                String target = (String) graphComponent.getTarget().getValue();
                if (!(VertexHashMap.containsKey(source) && VertexHashMap.containsKey(target))) {
                    graph.getModel().remove(graphComponent);
                }
            }
        }
        graph.clearSelection();
    }

    private void graphTransposition() {
        graph.clearSelection();
        graph.selectAll();
        Object[] cells = graph.getSelectionCells();
        for (Object cell : cells) {
            mxCell graphComponent = (mxCell) cell;
            if (graphComponent.isEdge()) {
                Object source = graphComponent.getSource();
                Object target = graphComponent.getTarget();
                Object parent = graph.getDefaultParent();
                graph.getModel().remove(graphComponent);
                graph.insertEdge(parent, null, "", target, source);
            }
        }
        graph.clearSelection();
    }

    private void colorConnectivityComponents() {
        int colorIndex = 0;
        for (String elem : strongConnectivityComponents) {
            if (elem == null) {
                colorIndex++;
                colorIndex %= 6;
            } else {
                changeColor(VertexHashMap.get(elem), colors[colorIndex]);
            }
        }
    }

    private void discolorVertexes() {
        graph.clearSelection();
        graph.selectAll();
        Object[] cells = graph.getSelectionCells();
        for (Object cell : cells) {
            mxCell graphComponent = (mxCell) cell;
            if (graphComponent.isVertex()) {
                changeColor(cell, "#C3D9FF");
            }
        }
        graph.clearSelection();
    }
}

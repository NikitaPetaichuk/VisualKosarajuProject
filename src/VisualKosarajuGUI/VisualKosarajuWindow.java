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

public class VisualKosarajuWindow extends JFrame {
    private static mxGraph graph  = new mxGraph();
    private static HashMap VertexHashMap = new HashMap();
    private boolean canBeEditted = true;
    private mxGraphComponent graphComponent;
    private JTextField VertexNameField;
    private JTextArea messageArea;
    private mxCell graphElement;

    public VisualKosarajuWindow() {
        super("Visual Kosaraju");
        initializeGUI();
    }

    public static mxGraph getGraph() {
        return graph;
    }

    public static HashMap getVertexHashMap() {
        return VertexHashMap;
    }

    private void initializeGUI() {
        setSize(new Dimension(630, 700));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT));

        createGraphComponent();
        createVertexLabel();
        createVertexNameField();
        createAddVertexButton();
        createAddEdgeButton();
        createDeleteGraphElemButton();
        createHorisontalSeparator();
        createStartButton();
        createNextStepButton();
        createPrevStepButton();
        createToTheEndButton();
        createToTheBeginButton();
        createResetButton();
        createMessageArea();
    }

    private void createGraphComponent() {
        graphComponent = new mxGraphComponent(graph);
        graphComponent.setPreferredSize(new Dimension(600,400));
        graphComponent.getGraphControl().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {
                graphElement = (mxCell) graphComponent.getCellAt(e.getX(), e.getY());
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        getContentPane().add(graphComponent);
    }

    private void createVertexLabel() {
        JLabel label = new JLabel("Имя вершины:");
        label.setPreferredSize(new Dimension(90, 21));
        getContentPane().add(label);
    }

    private void createHorisontalSeparator() {
        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setPreferredSize(new Dimension(630, 10));
        getContentPane().add(separator);
    }

    private void createVertexNameField() {
        VertexNameField = new JTextField();
        VertexNameField.setPreferredSize(new Dimension(505, 21));
        getContentPane().add(VertexNameField);
    }

    private void createStartButton() {
        JButton StartButton = new JButton("Начать визуализацию");
        StartButton.setPreferredSize(new Dimension(196, 25));
        StartButton.addActionListener((ActionEvent e) -> {
            canBeEditted = false;
        });
        getContentPane().add(StartButton);
    }

    private void createNextStepButton() {
        JButton NextStepButton = new JButton("Следующий шаг");
        NextStepButton.setPreferredSize(new Dimension(196, 25));
        getContentPane().add(NextStepButton);
    }

    private void createPrevStepButton() {
        JButton PrevStepButton = new JButton("Предыдущий шаг");
        PrevStepButton.setPreferredSize(new Dimension(196, 25));
        getContentPane().add(PrevStepButton);
    }

    private void createToTheEndButton() {
        JButton ToTheEndButton = new JButton("Доработать до конца");
        ToTheEndButton.setPreferredSize(new Dimension(196, 25));
        getContentPane().add(ToTheEndButton);
    }

    private void createToTheBeginButton() {
        JButton ToTheBeginButton = new JButton("Вернуться в начало");
        ToTheBeginButton.setPreferredSize(new Dimension(196, 25));
        getContentPane().add(ToTheBeginButton);
    }

    private void createResetButton() {
        JButton ResetButton = new JButton("Сброс");
        ResetButton.setPreferredSize(new Dimension(196, 25));
        ResetButton.addActionListener((ActionEvent e) -> {
            canBeEditted = true;
        });
        getContentPane().add(ResetButton);
    }

    private void createAddVertexButton() {
        JButton AddVertexButton = new JButton("Добавить вершину");
        AddVertexButton.setPreferredSize(new Dimension(196, 25));
        AddVertexButton.addActionListener((ActionEvent e) -> {
            if (canBeEditted) {
                VertexAdditor additor = new VertexAdditor(VertexNameField.getText());
            } else {
                JOptionPane.showMessageDialog(null, "Нельзя модифицировать граф в данный момент");
            }
        });
        getContentPane().add(AddVertexButton);
    }

    private void createAddEdgeButton() {
        JButton AddEdgeButton = new JButton("Добавить ребро");
        AddEdgeButton.setPreferredSize(new Dimension(197, 25));
        AddEdgeButton.addActionListener((ActionEvent e) -> {
            if (canBeEditted) {
                EdgeAdditor additor = new EdgeAdditor();
            } else {
                JOptionPane.showMessageDialog(null, "Нельзя модифицировать граф в данный момент");
            }
        });
        getContentPane().add(AddEdgeButton);
    }

    private void createDeleteGraphElemButton() {
        JButton DeleteGraphElemButton = new JButton("Удалить элемент");
        DeleteGraphElemButton.setPreferredSize(new Dimension(196, 25));
        DeleteGraphElemButton.addActionListener((ActionEvent e) -> {
            if (canBeEditted) {
                VertexHashMap.remove(graphElement.getValue());
                graph.getModel().remove(graphElement);
            } else {
                JOptionPane.showMessageDialog(null, "Нельзя модифицировать граф в данный момент");
            }
        });
        getContentPane().add(DeleteGraphElemButton);
    }

    private void createMessageArea() {
        messageArea = new JTextArea();
        messageArea.setPreferredSize(new Dimension(600, 100));
        messageArea.setEnabled(false);
        messageArea.setBackground(new Color(0x000000));
        messageArea.setForeground(new Color(0xFFFFFF));
        messageArea.setFont(new Font("CourierNew", Font.PLAIN, 14));
        getContentPane().add(messageArea);
    }
}

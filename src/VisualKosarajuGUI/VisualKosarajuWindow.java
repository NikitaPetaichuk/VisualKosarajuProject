package VisualKosarajuGUI;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

class IncorrectJSONFileContentException extends Exception {
    IncorrectJSONFileContentException() {
        super("Incorrect content of JSON graph file.");
    }
}

class VisualKosarajuWindow extends JFrame {
    private static mxGraph graph  = new mxGraph();
    private HashMap<String, Object> VertexHashMap = new HashMap<>();
    private String[] algorithmStepTrace;
    private List<String> strongConnectivityComponents;
    private int indexOfAlgorithmStep;
    private int indexOfTransposition;
    private boolean isEditMode;

    private String[] colors;
    private static final String STANDARD_COLOR = "#C3D9FF";
    private static final String CHOSEN_COLOR = "#FFD9C3";

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
        JMenu menu = new JMenu("Программа");
        JMenuItem openFileMenu = new JMenuItem(new openGraphJSONFile());
        JMenuItem helpMenu = new JMenuItem(new openHelpMenuAction());
        JMenuItem authorsMenu = new JMenuItem(new openAuthorsMenuAction());

        menu.add(openFileMenu);
        menu.add(new JSeparator());
        menu.add(helpMenu);
        menu.add(authorsMenu);
        menuBar.add(menu);
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
                            String vertexName = JOptionPane.showInputDialog("Введите имя вершины");
                            addVertex(vertexName, e.getX(), e.getY());
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
            if (isNoGraph()) {
                JOptionPane.showMessageDialog(null, "Нет графа");
                return;
            }
            if (isEditMode) {
                isEditMode = false;
                VisualKosarajuAlgorithmProxy algorithmProxy = new VisualKosarajuAlgorithmProxy();
                strongConnectivityComponents = algorithmProxy.getStrongConnectivityComponents();
                algorithmStepTrace = algorithmProxy.createAlgorithmStepTrace();
                indexOfAlgorithmStep = 0;
                findIndexOfTransposition();
                graphTransposition();
                logMessage("АКТИВАЦИЯ РЕЖИМА ВИЗУАЛИЗАЦИИ\n\n");
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
            goToNextStep();
            logAlgorithmStep(indexOfAlgorithmStep);
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
            goToPrevStep();
            logAlgorithmStep(indexOfAlgorithmStep);
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
            logAlgorithmStep(indexOfAlgorithmStep);
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
            logAlgorithmStep(indexOfAlgorithmStep);
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
                logMessage("\nВЫХОД ИЗ РЕЖИМА ВИЗУАЛИЗАЦИИ\n\n");
            }
        });
        getContentPane().add(ResetButton);
    }

    private void createMessageArea() {
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setBackground(new Color(0x000000));
        messageArea.setForeground(new Color(0xFFFFFF));
        messageArea.setFont(new Font("CourierNew", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(600, 150));
        getContentPane().add(scrollPane);
    }

    private boolean isNoGraph() {
        graph.clearSelection();
        graph.selectAll();
        Object[] cells = graph.getSelectionCells();
        graph.clearSelection();
        return cells.length == 0;
    }

    private void addVertex(String vertexName, int x, int y) {
        if (vertexName != null) {
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
    }

    private void addEdge(String sourceVertex, String targetVertex) {
        Object parent = graph.getDefaultParent();
        Object source = VertexHashMap.get(sourceVertex);
        Object target = VertexHashMap.get(targetVertex);
        graph.insertEdge(parent, null, "", source, target);
    }

    private void deleteGraph() {
        String[] vertexes = VertexHashMap.keySet().toArray(new String[0]);
        for (String vertex : vertexes) {
            deleteVertex(VertexHashMap.get(vertex));
        }
    }

    private void deleteVertex(Object vertexToDelete) {
        String nameOfDeletedVertex = ((mxCell) vertexToDelete).getValue().toString();
        VertexHashMap.remove(nameOfDeletedVertex);
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

    private void findIndexOfTransposition() {
        for (int i = 0; i < algorithmStepTrace.length; i++) {
            if (algorithmStepTrace[i] == null) {
                indexOfTransposition = i;
                break;
            }
        }
    }

    private void goToNextStep() {
        String nowVertex = algorithmStepTrace[indexOfAlgorithmStep];
        if (nowVertex != null && !nowVertex.equals("")) {
            changeColor(VertexHashMap.get(nowVertex), STANDARD_COLOR);
        }
        indexOfAlgorithmStep++;
        String nextVertex = algorithmStepTrace[indexOfAlgorithmStep];
        if (indexOfAlgorithmStep == algorithmStepTrace.length - 1) {
            colorConnectivityComponents();
        } else if (nextVertex == null) {
            graphTransposition();
        } else if (!nextVertex.equals("")) {
            changeColor(VertexHashMap.get(nextVertex), CHOSEN_COLOR);
        }
    }

    private void goToPrevStep() {
        String nowVertex = algorithmStepTrace[indexOfAlgorithmStep];
        if (indexOfAlgorithmStep == algorithmStepTrace.length - 1) {
            discolorVertexes();
        } else if (nowVertex == null) {
            graphTransposition();
        } else if (!nowVertex.equals("")) {
            changeColor(VertexHashMap.get(nowVertex), STANDARD_COLOR);
        }
        indexOfAlgorithmStep--;
        String prevVertex = algorithmStepTrace[indexOfAlgorithmStep];
        if (prevVertex != null && !prevVertex.equals("")) {
            changeColor(VertexHashMap.get(prevVertex), CHOSEN_COLOR);
        }
    }

    private void logAlgorithmStep(int indexOfAlgorithmStep) {
        if (indexOfAlgorithmStep == algorithmStepTrace.length - 1) {
            String strongConnectivityComponentsReport = createStrongConnectivityComponentsReport();
            logMessage("Окончание работы алгоритма. Компоненты сильной связности:\n");
            logMessage(strongConnectivityComponentsReport);
        } else if (indexOfAlgorithmStep == indexOfTransposition) {
            logMessage("Транспонирование графа.\n");
        } else if (indexOfAlgorithmStep == 0) {
            logMessage("Начало работы алгоритма.\n");
        } else if (algorithmStepTrace[indexOfAlgorithmStep].equals("")) {
            logMessage("Выход из ветки DFS.\n");
        } else {
            String nameOfVertex = algorithmStepTrace[indexOfAlgorithmStep];
            logMessage("Переход в вершину " + nameOfVertex + ".\n");
        }
        logMessage("- - - - - - - - - - - - - - -\n");
    }

    private void logMessage(String message) {
        String nowLogText = messageArea.getText() + message;
        messageArea.setText(nowLogText);
    }

    private String createStrongConnectivityComponentsReport() {
        StringBuilder report = new StringBuilder("{");
        for (String elem : strongConnectivityComponents) {
            if (elem == null) {
                String modifiedReport = report.subSequence(0, report.length() - 2).toString();
                report = new StringBuilder(modifiedReport + "}\n{");
                continue;
            }
            report.append(elem);
            report.append(", ");
        }
        String modifiedReport = report.subSequence(0, report.length() - 2).toString();
        report = new StringBuilder(modifiedReport + "}\n");
        return report.toString();
    }

    private class openHelpMenuAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        openHelpMenuAction() {
            putValue(NAME, "Помощь");
        }

        public void actionPerformed(ActionEvent e) {
            new HelpMenu();
        }
    }

    private class openAuthorsMenuAction extends AbstractAction {
        private static final long serialVersionUID = 1L;

        openAuthorsMenuAction() {
            putValue(NAME, "Контакты");
        }

        public void actionPerformed(ActionEvent e) {
            new AuthorsMenu();
        }
    }

    private class openGraphJSONFile extends AbstractAction {
        private static final long serialVersionUID = 1L;

        openGraphJSONFile() {
            putValue(NAME, "Открыть файл с графом");
        }

        public void actionPerformed(ActionEvent e) {
            if (isEditMode) {
                JFileChooser fileChooser = new JFileChooser();
                int returnCode = fileChooser.showDialog(null, "Открыть файл с графом");
                if (returnCode == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    if (!file.getName().endsWith(".json")) {
                        JOptionPane.showMessageDialog(null, "Файл не формата .json");
                        return;
                    }
                    try {
                        String fileContent = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                        deleteGraph();
                        parseJSONFile(fileContent);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Возникли проблемы с чтением файла");
                    } catch (ParseException ex) {
                        JOptionPane.showMessageDialog(null, "Некорректный формат json файла");
                    } catch (IncorrectJSONFileContentException ex) {
                        JOptionPane.showMessageDialog(null, "Некорректное содержимое json файла");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Нельзя открывать файл во время виртуализации");
            }
        }

        private void parseJSONFile(String JSONString) throws ParseException, IncorrectJSONFileContentException {
            JSONParser parser = new JSONParser();
            JSONObject mainJSON = (JSONObject) parser.parse(JSONString);
            JSONArray vertexes = (JSONArray) Optional.ofNullable(mainJSON.get("Vertexes")).orElse(null);
            if (vertexes == null) {
                throw new IncorrectJSONFileContentException();
            }
            createVertexes(vertexes);
            JSONObject edges = (JSONObject) Optional.ofNullable(mainJSON.get("Edges")).orElse(null);
            if (edges == null) {
                throw new IncorrectJSONFileContentException();
            }
            createEdges(edges);
        }

        private void createVertexes(JSONArray vertexes) {
            int horizontalCoordinate = 100;
            Iterator<String> iterator = vertexes.iterator();
            for (int verticalCoordinateCoefficient = 0; iterator.hasNext(); verticalCoordinateCoefficient++) {
                String vertex = iterator.next();
                int verticalCoordinate = 80 + (verticalCoordinateCoefficient / 3) * 100;
                addVertex(vertex, horizontalCoordinate, verticalCoordinate);
                horizontalCoordinate += 200;
                horizontalCoordinate %= 600;
            }
        }

        private void createEdges(JSONObject edges) throws IncorrectJSONFileContentException{
            String[] vertexes = VertexHashMap.keySet().toArray(new String[0]);
            for (String source : vertexes) {
                JSONArray neighbors = (JSONArray) Optional.ofNullable(edges.get(source)).orElse(null);
                if (neighbors != null) {
                    for (String target : (Iterable<String>) neighbors) {
                        if (VertexHashMap.containsKey(target)) {
                            addEdge(source, target);
                        } else {
                            throw new IncorrectJSONFileContentException();
                        }
                    }
                }
            }
        }
    }
}

package VisualKosarajuGUI;

import javax.swing.*;
import java.awt.*;

class HelpMenu extends JFrame {
    HelpMenu() {
        super("Visual Kosaraju - Помощь");
        setSize(new Dimension(550, 300));
        getContentPane().setLayout(new FlowLayout());

        String contentOfTextArea = createHelpMenuText();
        JTextArea textArea = new JTextArea();
        textArea.setText(contentOfTextArea);
        textArea.setEditable(false);
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(Color.BLACK);
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(530, 280));
        getContentPane().add(scrollPane);
        pack();
        setVisible(true);
    }

    private String createHelpMenuText() {
        return "РИСОВАНИЕ ГРАФА:\n\n" +
                "-) Левая кнопка мыши - перемещение элементов, создание рёбер\n" +
                "   (нажатие на середину вершины, провод стрелки до нужной вершины);\n" +
                "-) Правая кнопка мыши - удаление элемента, либо создание вершины,\n" +
                "   если нажать на пустом месте;\n\n" +
                "ОТКРЫТИЕ ГРАФА ИЗ ФАЙЛА:\n\n" +
                "Графы должны храниться в файлах .json в следующем формате:\n" +
                "{\n" +
                "    \"Vertexes\": [list of vertexes],\n" +
                "    \"Edges\": {\n" +
                "         \"vertex_from_list\": [list of neighbor vertexes],\n" +
                "         ...\n" +
                "    }\n" +
                "}\n\n" +
                "УПРАВЛЕНИЕ ВИЗУАЛИЗАЦИЕЙ:\n\n" +
                "-) Начать визуализацию - начать пошаговую работу алгоритма над\n" +
                "   данным графом;\n" +
                "-) Следующий шаг - переход на следующий шаг алгоритма;\n" +
                "-) Предудущий шаг - возврат на предыдущий шаг алгоритма;\n" +
                "-) Доработать до конца - переход на конечный шаг работы алгоритма;\n" +
                "-) Вернуться в начало - переход на начальный этап работы алгоритма;\n" +
                "-) Сброс - прервать выполнение алгоритма/выход из режима визуализации;\n\n" +
                "В чёрном текстовом поле будет показано краткое описание текущего\n" +
                "шага алгоритма.";
    }
}

package VisualKosarajuGUI;

public class VisualKosarajuApplication {
    private VisualKosarajuApplication() {
         VisualKosarajuWindow window = new VisualKosarajuWindow();
         window.setVisible(true);
    }

    public static void main(String[] args) {
        new VisualKosarajuApplication();
    }
}

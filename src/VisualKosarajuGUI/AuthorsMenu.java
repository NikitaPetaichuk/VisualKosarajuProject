package VisualKosarajuGUI;

import javax.swing.*;
import java.awt.*;

class AuthorsMenu extends JFrame {
    AuthorsMenu() {
        super("Visual Kosaraju - Контакты");
        getContentPane().setPreferredSize(new Dimension(330,165));
        getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel nameDavydov = new JLabel("1) Давыдов А.А. - тимлид, старший разработчик");
        JLabel emailDavydov = new JLabel("Почта - casha_davydov@mail.ru");
        JLabel nameChemova = new JLabel("2) Чемова К.А. - разработчик, тестировщик");
        JLabel emailChemova = new JLabel("Почта - chemovaks@mail.ru");
        JLabel namePet = new JLabel("3) Пэтайчук Н.Г. - разработчик, дизайнер");
        JLabel emailPet = new JLabel("Почта - pet.ai.4.uk@yandex.ru");
        JSeparator firstSeparator = new JSeparator();
        firstSeparator.setPreferredSize(new Dimension(320, 10));
        JSeparator secondSeparator = new JSeparator();
        secondSeparator.setPreferredSize(new Dimension(320, 10));

        getContentPane().add(nameDavydov);
        getContentPane().add(emailDavydov);
        getContentPane().add(firstSeparator);
        getContentPane().add(nameChemova);
        getContentPane().add(emailChemova);
        getContentPane().add(secondSeparator);
        getContentPane().add(namePet);
        getContentPane().add(emailPet);
        pack();
        setVisible(true);
    }
}

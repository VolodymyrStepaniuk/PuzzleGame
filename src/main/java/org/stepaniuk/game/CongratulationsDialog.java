package org.stepaniuk.game;

import javax.swing.*;
import java.awt.*;

public class CongratulationsDialog {
    // Метод для відображення діалогового вікна з вітаннями
    public static void showCongratulationsDialog(JPanel panel) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Вітаємо!");
        dialog.setModal(true);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(panel);

        JPanel contentPanel = new JPanel(new BorderLayout());

        JLabel messageLabel = new JLabel("<html>Ви вирішили головоломку!<br>Бажаєте продовжити гру?</html>", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        contentPanel.add(messageLabel, BorderLayout.CENTER);

        JButton yesButton = new JButton("Так");
        yesButton.addActionListener(e -> dialog.dispose());

        JButton noButton = new JButton("Ні");
        noButton.addActionListener(e -> {
            dialog.dispose();  // Закрити діалогове вікно
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
            frame.dispose();  // Закрити JFrame
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setContentPane(contentPanel);
        dialog.setVisible(true);
    }
}

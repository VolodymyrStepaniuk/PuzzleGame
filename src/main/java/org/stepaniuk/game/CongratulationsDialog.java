package org.stepaniuk.game;

import lombok.extern.log4j.Log4j2;

import javax.swing.*;
import java.awt.*;
@Log4j2
public class CongratulationsDialog {
    // Метод для відображення діалогового вікна з вітаннями
    private static boolean needToClose = false;
    public static void showCongratulationsDialog(JPanel panel) {
        log.info("Користувач вирішив головоломку та отримав привітальне вікно");
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
        yesButton.addActionListener(e -> {
            dialog.dispose();
        });

        JButton noButton = new JButton("Ні");
        noButton.addActionListener(e -> {
            dialog.dispose();  // Закрити діалогове вікно
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
            frame.dispose();  // Закрити JFrame
            needToClose=true;
            log.info("Користувач вирішив завершити гру");
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.setContentPane(contentPanel);
        dialog.setVisible(true);
    }

    public static boolean isNeedToClose() {
        return needToClose;
    }
}

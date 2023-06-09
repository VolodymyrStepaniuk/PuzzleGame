package org.stepaniuk.game;

import lombok.extern.log4j.Log4j2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
@Log4j2
public class PuzzleGame {
    private final JPanel panel;  // Панель, на якій розміщена гральна дошка
    private final JLabel[][] labels;  // Масив міток, що представляють тайли гральної дошки
    private final int[][] board;  // Масив, що представляє стан гральної дошки
    private int emptyRow;  // Рядок пустої комірки
    private int emptyCol;  // Стовпець пустої комірки
    private final int sizeOfGame;  // Розмір гральної дошки
    private final JButton shuffleButton;  // Кнопка для перемішування гральної дошки

    public PuzzleGame(int sizeOfGame) {
        log.info("Гра була ініціалізована");
        this.sizeOfGame = sizeOfGame;

        emptyRow = this.sizeOfGame - 1;
        emptyCol = this.sizeOfGame - 1;

        panel = new JPanel(new GridLayout(this.sizeOfGame, this.sizeOfGame));
        labels = new JLabel[this.sizeOfGame][this.sizeOfGame];
        board = new int[this.sizeOfGame][this.sizeOfGame];

        shuffleButton = new JButton("Перемішати");
        shuffleButton.addActionListener(e -> shuffleBoard());
        shuffleButton.setBackground(Color.PINK);

        initializeBoard();
        createLabels();
        shuffleBoard();
    }
//    Метод initializeBoard заповнює гральну дошку значеннями від 1 до sizeOfGame^2,
//    розміщуючи їх у відповідні комірки. Також встановлює значення 0 для пустої комірки.
    private void initializeBoard() {
        log.info("Ініціалізація ігрової дошки");
        int value = 1;
        for (int row = 0; row < sizeOfGame; row++) {
            for (int col = 0; col < sizeOfGame; col++) {
                board[row][col] = value;
                value++;
            }
        }
        board[emptyRow][emptyCol] = 0;  // Встановлення пустої комірки
    }
//    Метод shuffleBoard перемішує гральну дошку шляхом виконання випадкових переміщень комірок.
//    Він генерує випадкові рядки та стовпці і викликає метод move(row, col), щоб перемістити вибрану комірку.
    private void shuffleBoard() {
        log.info("Дошка буда перемішана");
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int row = random.nextInt(sizeOfGame);
            int col = random.nextInt(sizeOfGame);
            move(row, col);  // Виконання переміщення комірки
        }
    }
//    Метод createLabels створює JLabel для кожної комірки і задає йому зображення іконки.
//    Також встановлює розмір, рамку та додає MouseListener, який відслідковує кліки на мітці.
//    Усі створені Jlabel зберігаються в масиві labels і додаються на панель.
    private void createLabels() {
        log.info("Був створений масив labels з візуальним представленням таблиці");
        ImageIcon[] icons = Sprite.convertToIcons(sizeOfGame);  // Конвертація зображення в масив іконок
        int value = 0;
        for (int row = 0; row < sizeOfGame; row++) {
            for (int col = 0; col < sizeOfGame; col++) {
                JLabel label = new JLabel(icons[value]);
                label.setPreferredSize(new Dimension(80, 80));
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                label.addMouseListener(new LabelMouseListener(row, col));
                value++;
                labels[row][col] = label;
                panel.add(label);
            }
        }
    }
//    Метод move виконує переміщення комірки на гральній дошці.
//    Він перевіряє, чи вибрана комірка сусідня з пустою коміркою,
//    і якщо так, то виконує переміщення.
//    Він також оновлює значення пустої комірки та оновлює відображення label на панелі.
    private void move(int row, int col) {
        if (isAdjacent(row, col)) {  // Перевірка, чи обрана комірка сусідня з пустою коміркою
            moveImage(row, col);  // Переміщення зображення label
            log.info("Картинка та комірка була переміщена на позицію: ("+row+","+col+")");
            int temp = board[row][col];
            board[row][col] = 0;
            board[emptyRow][emptyCol] = temp;
            emptyRow = row;
            emptyCol = col;
            updateLabels();  // Оновлення label на панелі
        }
    }
//    Метод moveImage виконує переміщення зображення між label'ами.
//    Він отримує зображення з label вибраної комірки,
//    видаляє його з label та встановлює його на label пустої комірки.
    private void moveImage(int row, int col) {
        ImageIcon icon = (ImageIcon) labels[row][col].getIcon();  // Отримання іконки з мітки
        labels[row][col].setIcon(null);  // Встановлення пустої іконки для мітки
        labels[emptyRow][emptyCol].setIcon(icon);  // Встановлення іконки в пусту
        log.info("Картинка була переміщена на пусту позицію");
    }
//    Метод isAdjacent перевіряє, чи є вибрана комірка сусідньою до пустої комірки.
//    Він порівнює різницю між рядками та стовпцями обох комірок та повертає true, якщо вони сусідні.
    private boolean isAdjacent(int row, int col) {
        log.info("Було перевірено,чи є вибрана комірка сусідньою до пустої комірки.");
        return (Math.abs(emptyRow - row) == 1 && emptyCol == col) || (Math.abs(emptyCol - col) == 1 && emptyRow == row);
    }
//    Метод updateLabels оновлює відображення label на панелі гральної дошки.
//    Він видаляє всі labels з панелі, додає оновлені labels з масиву та оновлює відображення панелі.
    private void updateLabels() {
        panel.removeAll();  // Видалення всіх міток з панелі
        for (int row = 0; row < sizeOfGame; row++) {
            for (int col = 0; col < sizeOfGame; col++) {
                JLabel label = labels[row][col];
                panel.add(label);  // Додавання label на панель
            }
        }
        panel.revalidate();  // Перевірка і оновлення панелі
        panel.repaint();  // Перемалювання панелі
        log.info("Було оновлено всі label з масиву labels");
    }
//    Метод checkIfSolved перевіряє, чи гра розв'язана.
//    Він перевіряє, чи значення в кожній комірці гральної дошки відповідає очікуваному значенню.
//    Якщо всі значення вірні, він відображає вікно привітання,
//    перемішує гральну дошку та підготовлює гру до наступного раунду.
    private void checkIfSolved() {
        boolean solved = true;
        int value = 1;
        for (int row = 0; row < sizeOfGame; row++) {
            for (int col = 0; col < sizeOfGame; col++) {
                if (board[row][col] != value && value != (sizeOfGame * sizeOfGame)) {
                    solved = false;
                    break;
                }
                value++;
            }
        }
        if (solved) {
            log.info("Було знайдено вирішення головоломки");
            CongratulationsDialog.showCongratulationsDialog(panel);  // Відображення діалогового вікна з привітаннями
            if(!CongratulationsDialog.isNeedToClose())
                shuffleBoard();  // Перемішування гральної дошки
        }
    }
//    Метод createGameBoard створює панель для гральної дошки.
//    Він додає гральну дошку на панель у центрі та кнопку перемішування внизу.
//    Повертає створену панель.
    public JPanel createGameBoard() {
        log.info("Ініціалізація візуальної частини ігрової дошки");
        JPanel gamePanel = new JPanel(new BorderLayout());
        gamePanel.add(panel, BorderLayout.CENTER);
        gamePanel.add(shuffleButton, BorderLayout.SOUTH);
        return gamePanel;
    }

    private class LabelMouseListener extends MouseAdapter {
        private final int row;
        private final int col;

        public LabelMouseListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            move(row, col);  // Виконання переміщення комірки при кліку на мітку
            checkIfSolved();  // Перевірка, чи гра вирішена після кожного ходу
        }
    }
}
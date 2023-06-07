package org.stepaniuk;

import org.stepaniuk.game.PuzzleGame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Створення об'єкту гри PuzzleGame з розміром 4х4
        PuzzleGame puzzleGame = new PuzzleGame(4);

        // Створення головного вікна JFrame
        JFrame frame = new JFrame("Пазли by Stepaniuk Volodymyr");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Встановлення дії при закритті вікна
        frame.setSize(500, 500);  // Встановлення розміру вікна
        frame.add(puzzleGame.createGameBoard());  // Додавання гральної дошки до вікна
        frame.setVisible(true);  // Відображення вікна
        frame.setResizable(false);  // Вимкнення зміни розміру вікна
    }
}